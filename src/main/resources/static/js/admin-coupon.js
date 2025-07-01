// admin-coupon.js

/**
 * 이 이벤트 리스너는 HTML 문서가 완전히 로드되고 준비되었을 때 내부 코드를 실행시킵니다.
 * 이렇게 하면 HTML 요소가 미처 만들어지기 전에 스크립트가 실행되어 발생하는 오류를 방지할 수 있습니다.
 */
document.addEventListener('DOMContentLoaded', () => {

    // --- 일반 쿠폰 등록 모달의 유효 기간 타입 선택 관련 로직 ---
    const createModal = document.getElementById('modal-normal-coupon');

    if (createModal) {
        const periodTypeRadios = createModal.querySelectorAll('input[name="periodType"]');
        const fixedPeriodInputs = createModal.querySelector('#fixed-period-inputs');
        const relativePeriodInputs = createModal.querySelector('#relative-period-inputs');

        periodTypeRadios.forEach(radio => {
            radio.addEventListener('change', (event) => {
                if (event.target.value === 'FIXED') {
                    fixedPeriodInputs.classList.remove('hidden');
                    relativePeriodInputs.classList.add('hidden');
                } else {
                    fixedPeriodInputs.classList.add('hidden');
                    relativePeriodInputs.classList.remove('hidden');
                }
            });
        });
    }

    // --- 도서 쿠폰 등록 모달의 도서 검색 로직 ---
    const bookCouponModal = document.getElementById('modal-book-coupon');

    if (bookCouponModal) {
        const dummyBooks = [
            { id: 1, title: 'Do it! 자바 프로그래밍 입문' },
            { id: 2, title: '이것이 자바다' },
            { id: 3, title: '객체지향의 사실과 오해' },
            { id: 4, title: '모던 자바스크립트 Deep Dive' },
            { id: 5, title: '스프링 부트와 AWS로 혼자 구현하는 웹 서비스' },
            { id: 6, title: '코틀린 인 액션 (Kotlin in Action)' },
            { id: 7, title: 'Clean Code(클린 코드)' },
            { id: 8, title: '실용주의 프로그래머' },
            { id: 9, title: '데이터베이스 개론' },
            { id: 10, title: '만들면서 배우는 클린 아키텍처' },
            { id: 11, title: '우리의 낙원에서 만나자'}
        ];
        const searchInput = bookCouponModal.querySelector('#bookSearchInput');
        const searchResultsContainer = bookCouponModal.querySelector('#bookSearchResults');
        const targetBookIdInput = bookCouponModal.querySelector('#targetBookId');
        const selectedBookDisplay = bookCouponModal.querySelector('#selectedBookNameDisplay');
        let debounceTimer;

        searchInput.addEventListener('input', (e) => {
            const query = e.target.value;
            clearTimeout(debounceTimer);
            if (query.length < 2) {
                searchResultsContainer.classList.add('hidden');
                return;
            }
            debounceTimer = setTimeout(() => {
                searchBooksLocally(query);
            }, 300);
        });

        function searchBooksLocally(query) {
            const lowerCaseQuery = query.toLowerCase();
            const filteredBooks = dummyBooks.filter(book =>
                book.title.toLowerCase().includes(lowerCaseQuery)
            );
            displayResults(filteredBooks);
        }

        function displayResults(books) {
            searchResultsContainer.innerHTML = '';
            if (books.length === 0) {
                searchResultsContainer.innerHTML = '<div class="p-2 text-gray-500">검색 결과가 없습니다.</div>';
            } else {
                books.forEach(book => {
                    const li = document.createElement('li');
                    li.textContent = book.title;
                    li.className = 'p-2 hover:bg-sky-100 cursor-pointer';
                    li.dataset.id = book.id;
                    li.dataset.title = book.title;
                    searchResultsContainer.appendChild(li);
                });
            }
            searchResultsContainer.classList.remove('hidden');
        }

        searchResultsContainer.addEventListener('click', (e) => {
            if (e.target.tagName === 'LI') {
                targetBookIdInput.value = e.target.dataset.id;
                selectedBookDisplay.textContent = e.target.dataset.title;
                searchResultsContainer.classList.add('hidden');
            }
        });

        document.addEventListener('click', (e) => {
            if (!bookCouponModal.contains(e.target)) {
                searchResultsContainer.classList.add('hidden');
            }
        });
    }

    // --- [추가] 카테고리 쿠폰 등록 모달의 2단 드롭다운 로직 ---
    const categoryCouponModal = document.getElementById('modal-category-coupon');

    if (categoryCouponModal) {
        // 모달 내의 요소들을 정확히 선택합니다.
        const parentSelect = categoryCouponModal.querySelector('#parentCategorySelect');
        const childSelect = categoryCouponModal.querySelector('#childCategorySelect');
        const targetIdInput = categoryCouponModal.querySelector('#targetCategoryIdInput');

        if (typeof categoryHierarchy === 'undefined' || categoryHierarchy === null) {
            console.error('카테고리 계층 구조 데이터(categoryHierarchy)가 페이지에 존재하지 않습니다.');
            // 데이터가 없으면 드롭다운을 비활성화 할 수 있습니다.
            parentSelect.disabled = true;
            childSelect.disabled = true;
            return; // 로직 실행 중단
        }

        parentSelect.addEventListener('change', (event) => {
            const selectedParentId = event.target.value;

            childSelect.innerHTML = '<option value="">-- 하위 카테고리 선택 --</option>';

            // hidden input 값을 우선 상위 카테고리 ID로 설정합니다.
            targetIdInput.value = selectedParentId;

            if (selectedParentId) {
                // 전체 데이터에서 선택된 ID와 일치하는 부모 객체를 찾습니다.
                const selectedParent = categoryHierarchy.find(p => p.id == selectedParentId);

                if (selectedParent && selectedParent.children) {
                    // 찾아낸 부모 객체의 children 리스트로 하위 드롭다운을 채웁니다.
                    selectedParent.children.forEach(child => {
                        const option = document.createElement('option');
                        option.value = child.id;
                        option.textContent = child.name;
                        childSelect.appendChild(option);
                    });
                }
            }
        });

        // 하위 카테고리 선택 이벤트 처리
        childSelect.addEventListener('change', (event) => {
            const selectedChildId = event.target.value;

            // 하위 카테고리가 선택되었다면 (빈 값이 아니라면)
            if (selectedChildId) {
                // hidden input 값을 하위 ID로 덮어씁니다.
                targetIdInput.value = selectedChildId;
            } else {
                // 하위 카테고리 선택을 해제하면, 다시 상위 카테고리 ID로 되돌립니다.
                targetIdInput.value = parentSelect.value;
            }
        });
    }

}); // DOMContentLoaded 이벤트 리스너 종료