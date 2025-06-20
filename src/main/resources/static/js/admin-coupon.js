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
        const fixedPeriodInputs = createModal.querySelector('#fixed-period-inputs');
        const relativePeriodInputs = createModal.querySelector('#relative-period-inputs');

        periodTypeRadios.forEach(radio => {
            radio.addEventListener('change', (event) => {
                if (event.target.value === 'FIXED') {
                    // '고정 유효기간' 선택 시
                    fixedPeriodInputs.classList.remove('hidden'); // 고정 기간 입력창 보이기
                    relativePeriodInputs.classList.add('hidden');    // 상대 기간 입력창 숨기기
                } else {
                    // '상대 유효기간' 선택 시
                    fixedPeriodInputs.classList.add('hidden');      // 고정 기간 입력창 숨기기
                    relativePeriodInputs.classList.remove('hidden'); // 상대 기간 입력창 보이기
                }
            });
        });
    }

    // --- [추가됨] 도서 쿠폰 등록 모달의 도서 검색 로직 ---
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
                // [변경] fetch 대신 로컬 함수를 호출합니다.
                searchBooksLocally(query);
            }, 300);
        });

        // [2. API 호출 대신 더미 데이터를 필터링하는 가상 함수]
        // 나중에 API 완성 후 원래의 async/fetch 버전으로 교체하세요.
        function searchBooksLocally(query) {
            const lowerCaseQuery = query.toLowerCase();

            // 더미 데이터에서 검색어와 일치하는 책을 필터링합니다.
            const filteredBooks = dummyBooks.filter(book =>
                book.title.toLowerCase().includes(lowerCaseQuery)
            );

            // 화면에 결과를 표시하는 함수는 그대로 재사용합니다.
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

                // 1. 숨겨진 input에는 'ID'를 저장합니다. (서버 전송용)
                targetBookIdInput.value = selectedId;

                // 2. '구매 조건' 영역의 div에는 '도서명' 텍스트를 표시합니다.
                selectedBookDisplay.textContent = selectedTitle;

                searchResultsContainer.classList.add('hidden');
            }
        });

        document.addEventListener('click', (e) => {
            if (!bookCouponModal.contains(e.target)) {
                searchResultsContainer.classList.add('hidden');
            }
        });
    }
});

    /**
     * 서버에 도서 검색을 요청하고 결과를 표시하는 '실제' 함수
     * @param {string} query - 검색어
     */
    // async function searchBooks(query) {
    //     try {
    //         // ▼▼▼ 바로 이 부분의 URL 경로를 실제 API 경로로 맞춰주시면 됩니다 ▼▼▼
    //         const response = await fetch(`/admin/api/books/search?title=${query}`);
    //
    //         if (!response.ok) {
    //             throw new Error('서버 응답이 올바르지 않습니다.');
    //         }
    //         const books = await response.json();
    //         displayResults(books);
    //     } catch (error) {
    //         console.error('도서 검색 중 오류 발생:', error);
    //         searchResultsContainer.innerHTML = '<div class="p-2 text-red-500">검색 중 오류가 발생했습니다.</div>';
    //         searchResultsContainer.classList.remove('hidden');
    //     }
    // }
