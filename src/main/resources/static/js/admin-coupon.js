// admin-coupon.js

/**
 * 이 이벤트 리스너는 HTML 문서가 완전히 로드되고 준비되었을 때 내부 코드를 실행시킵니다.
 * 이렇게 하면 HTML 요소가 미처 만들어지기 전에 스크립트가 실행되어 발생하는 오류를 방지할 수 있습니다.
 */
document.addEventListener('DOMContentLoaded', () => {

    // --- 일반 쿠폰 등록 모달의 유효 기간 타입 선택 관련 로직 ---
    const createModal = document.getElementById('modal-normal-coupon');

    // '일반 쿠폰 등록 모달'이 현재 페이지에 존재할 때만 아래 코드를 실행합니다.
    if (createModal) {
        // 모달 내에서 요소를 정확히 찾기 위해 createModal.querySelector를 사용합니다.
        const periodTypeRadios = createModal.querySelectorAll('input[name="periodType"]');
        const fixedPeriodContainer = createModal.querySelector('#fixed-period-inputs');
        const relativePeriodContainer = createModal.querySelector('#relative-period-inputs');

        const fixedInputs = fixedPeriodContainer.querySelectorAll('input');
        const relativeInputs = relativePeriodContainer.querySelectorAll('input');

        periodTypeRadios.forEach(radio => {
            radio.addEventListener('change', (event) => {
                if (event.target.value === 'FIXED') {
                    // '고정 유효기간' 선택 시
                    fixedPeriodContainer.classList.remove('hidden'); // 고정 기간 입력창 보이기
                    fixedInputs.forEach(input => input.disabled = false); // 고정 기간 input 활성화

                    relativePeriodContainer.classList.add('hidden');    // 상대 기간 입력창 숨기기
                    relativeInputs.forEach(input => input.disabled = true); // 상대 기간 input 비활성화
                } else { // 'RELATIVE' 선택 시
                    fixedPeriodContainer.classList.add('hidden');      // 고정 기간 입력창 숨기기
                    fixedInputs.forEach(input => input.disabled = true); // 고정 기간 input 비활성화

                    relativePeriodContainer.classList.remove('hidden'); // 상대 기간 입력창 보이기
                    relativeInputs.forEach(input => input.disabled = false); // 상대 기간 input 활성화
                }
            });
        });

        // 페이지 로드 시, 기본으로 선택된 라디오 버튼에 맞게 상태를 초기화 해주는 것이 좋습니다.
        const initiallyChecked = createModal.querySelector('input[name="periodType"]:checked');
        if (initiallyChecked) {
            initiallyChecked.dispatchEvent(new Event('change'));
        }
    }

    // --- 도서 쿠폰 등록 모달의 도서 검색 로직 ---
    const bookCouponModal = document.getElementById('modal-book-coupon');

    if (bookCouponModal) {
        // [1. 테스트용 더미 데이터] - 나중에 API가 완성되면 이 부분은 삭제합니다.
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
            { id: 10, title: '만들면서 배우는 클린 아키텍처' }
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
                const selectedId = e.target.dataset.id;
                const selectedTitle = e.target.dataset.title;
                targetBookIdInput.value = selectedId;
                selectedBookDisplay.textContent = selectedTitle;
                searchResultsContainer.classList.add('hidden'); // 선택 후 목록 숨기기
            }
        });

        document.addEventListener('click', (e) => {
            if (!bookCouponModal.contains(e.target)) {
                searchResultsContainer.classList.add('hidden');
            }
        });
    }

    const modal = document.getElementById('editCouponModal');

    // '상세 보기/수정' 버튼들을 모두 찾습니다.
    const editButtons = document.querySelectorAll('.js-edit-coupon-btn');

    // 각 버튼에 클릭 이벤트를 추가합니다.
    editButtons.forEach(button => {
        button.addEventListener('click', function () {
            // 클릭된 버튼의 data-coupon 속성 값을 가져옵니다.
            const couponDataString = this.dataset.coupon;
            openEditModal(couponDataString); // 이제 함수를 호출합니다.
        });
    });

    // 모달을 여는 함수
    function openEditModal(couponDataString) {
        const coupon = JSON.parse(couponDataString);

        // --- 1. 고정 필드 값 설정 ---
        document.getElementById('modal_name').value = coupon.couponPolicyName; // DTO 필드명에 맞게 수정
        document.getElementById('modal_discountType').value = coupon.discountType === 'FIXED' ? '정액' : '정률';

        const autoIssueCheckbox = document.getElementById('modal_autoIssue');
        autoIssueCheckbox.checked = coupon.autoIssue;
        autoIssueCheckbox.disabled = true;

        // --- 2. 수정 가능 필드 값 설정 및 UI 제어 ---
        document.getElementById('modal_discountValue').value = coupon.discountValue;
        const discountUnit = document.getElementById('modal_discountUnit');
        const maxDiscountContainer = document.getElementById('modal_maxDiscount_container');

        if (coupon.discountType === 'FIXED') {
            discountUnit.textContent = '원';
            maxDiscountContainer.classList.add('hidden');
        } else { // 'RATE' 또는 'PERCENT'
            discountUnit.textContent = '%';
            maxDiscountContainer.classList.remove('hidden');
            document.getElementById('modal_maxDiscount').value = coupon.maxDiscount;
        }

        // 쿠폰 타입에 따른 사용 조건 필드 제어
        const minPurchaseContainer = document.getElementById('modal_minPurchase_container');
        // 사용 조건(책, 카테고리 등)을 표시할 다른 컨테이너도 추가할 수 있습니다.
        // 여기서는 일단 최소구매금액만 제어합니다.
        if (coupon.couponType === 'NORMAL') {
            minPurchaseContainer.style.display = 'block';
            document.getElementById('modal_minPurchase').value = coupon.minPurchase;
        } else {
            minPurchaseContainer.style.display = 'none'; // 일반 쿠폰이 아니면 최소구매금액 필드 숨김
        }

        // 유효 기간 타입에 따라 입력 필드 제어
        const daysContainer = document.getElementById('modal_period_days_container');
        const datesContainer = document.getElementById('modal_period_dates_container');

        if (coupon.validDays > 0) {
            daysContainer.style.display = 'block';
            datesContainer.style.display = 'none';
            document.getElementById('modal_validDays').value = coupon.validDays;
        } else {
            daysContainer.style.display = 'none';
            datesContainer.style.display = 'flex';
            document.getElementById('modal_startDate').value = coupon.startDate;
            document.getElementById('modal_endDate').value = coupon.endDate;
        }

        // --- 3. 모달 표시 ---
        modal.classList.remove('hidden');
    }

    // 모달을 닫는 함수 (이 함수는 전역 스코프에 있어야 할 수 있으므로, 일단 그대로 두거나
    // 닫기 버튼에도 이벤트 리스너를 붙이는 방식으로 변경할 수 있습니다.)
    // onclick="closeEditModal()" 이 잘 동작한다면 그대로 두어도 괜찮습니다.
    window.closeEditModal = function() {
        modal.classList.add('hidden');
    }
});