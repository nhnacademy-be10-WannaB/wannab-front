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
    // --- ✨ 도서 검색 결과의 '쿠폰 등록' 버튼 클릭 이벤트 처리 ✨ ---
    const bookCouponButtons = document.querySelectorAll('.open-book-coupon-modal');
    const bookCouponModal = document.getElementById('modal-book-coupon');

    if (bookCouponButtons.length > 0 && bookCouponModal) {
        const targetIdInput = bookCouponModal.querySelector('#targetBookId');
        const displayName = bookCouponModal.querySelector('#selectedBookNameDisplay');

        bookCouponButtons.forEach(button => {
            button.addEventListener('click', function() {
                // 클릭된 버튼의 data 속성에서 도서 ID와 이름을 가져옴
                const bookId = this.dataset.bookId;
                const bookName = this.dataset.bookName;

                // 모달 안의 숨겨진 input과 표시 영역에 값을 채워넣음
                targetIdInput.value = bookId;
                displayName.textContent = bookName;

                // Flowbite 등 사용하는 라이브러리의 모달 열기 함수 호출
                // 예시: new Modal(bookCouponModal).show();
                // 아래는 간단한 class 제어 예시
                bookCouponModal.classList.remove('hidden');
            });
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