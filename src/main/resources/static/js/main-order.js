function applyCoupon(selectElement) {
    const value = selectElement.value;
    const text = selectElement.options[selectElement.selectedIndex].text;
    const container = document.getElementById("coupon-container");
  
    // 이미 같은 쿠폰이 추가되어 있는지 확인
    const exists = [...container.children].some(
      el => el.dataset.value === value
    );
  
    if (value && !exists) {
      const couponDiv = document.createElement("div");
      couponDiv.className = "coupon-box";
      couponDiv.dataset.value = value;
      couponDiv.textContent = text;
  
      // 필요시 삭제 버튼 추가
      const removeBtn = document.createElement("button");
      removeBtn.textContent = "✖";
      removeBtn.onclick = () => container.removeChild(couponDiv);
  
      couponDiv.appendChild(removeBtn);
      container.appendChild(couponDiv);
    }
  
    // 선택 초기화
    selectElement.value = "";
  }

document.addEventListener("DOMContentLoaded", function () {
  const selects = document.querySelectorAll("select[id^='wpSelect']");
  selects.forEach(select => {
    // 초기 렌더링 시 포장지 가격 반영
    updateWrappingPrice(select);

    // 사용자가 선택 변경했을 때만 반영
    select.addEventListener("change", function () {
      updateWrappingPrice(this);
    });
  });
});

  // 포장지 선택 시 가격 세팅 및 결제 요약 업데이트
function updateWrappingPrice(selectElement) {
    const index = selectElement.getAttribute("data-index");
    const selectedOption = selectElement.options[selectElement.selectedIndex];
    const price = selectedOption.getAttribute("data-price") || 0;
    const priceInput = document.getElementById("wpPriceInput" + index);
    if (priceInput) {
      priceInput.value = price;
    }

    updateOrderSummary();
}

  // 결제 요약 실시간 계산 함수
  function updateOrderSummary() {
    const orderSummaryEl = document.querySelector(".order-summary");
    if (!orderSummaryEl) return;

    testBook = orderSummaryEl.getAttribute("data-total-book-price");
      console.log(testBook)
    const totalBookPrice = parseInt(orderSummaryEl.getAttribute("data-total-book-price"), 10) || 0;
    const shippingFee = parseInt(orderSummaryEl.getAttribute("data-shipping-fee"), 10) || 0;

    // 포장지 가격 합계 계산
    let wrappingTotal = 0;
    const priceInputs = document.querySelectorAll("input[name$='.wrappingPaperPrice']");
    priceInputs.forEach(input => {
      const val = parseInt(input.value, 10);
      if (!isNaN(val)) wrappingTotal += val;
    });

    // 사용 포인트 읽기 및 제한
    const usedPointsInput = document.getElementById("usedPointsInput");
    let usedPoints = 0;
    if (usedPointsInput) {
      usedPoints = parseInt(usedPointsInput.value, 10);
      if (isNaN(usedPoints) || usedPoints < 0) usedPoints = 0;

      const maxPoints = parseInt(usedPointsInput.getAttribute("max"), 10);
      if (!isNaN(maxPoints) && usedPoints > maxPoints) {
        usedPoints = maxPoints;
        usedPointsInput.value = maxPoints;
      }
    }

    // 포장지 가격 표시 업데이트
    const wrappingPriceSpan = document.getElementById("wrappingPriceDisplay");
    if (wrappingPriceSpan) {
      wrappingPriceSpan.textContent = "+" + wrappingTotal.toLocaleString() + "원";
    }

    // 사용 포인트 표시 업데이트
    const usedPointsDisplay = document.getElementById("usedPointsDisplay");
    if (usedPointsDisplay) {
      usedPointsDisplay.textContent = "-" + usedPoints.toLocaleString() + "원";
    }

    // 결제 예정금액 계산 및 표시
    const summaryTotalStrong = document.querySelector(".order-summary p.summary-total strong");
    if (summaryTotalStrong) {
      let finalAmount = totalBookPrice + wrappingTotal + shippingFee - usedPoints;
      console.log("totalBookPrice"+totalBookPrice)
      console.log("wrappingTotal"+wrappingTotal)
      console.log("shippingFee"+shippingFee)
      console.log("usedPoints"+usedPoints)
      if (finalAmount < 0) finalAmount = 0;
      summaryTotalStrong.textContent = finalAmount.toLocaleString() + "원";
    }
  }

  // 초기화 및 이벤트 바인딩
  document.addEventListener("DOMContentLoaded", function () {
    // 포장지 select 초기값 세팅 및 요약 업데이트
    const selects = document.querySelectorAll("select[id^='wpSelect']");
    selects.forEach(select => {
      updateWrappingPrice(select);
      select.addEventListener("change", () => updateWrappingPrice(select));
    });

    // 포인트 입력 이벤트 바인딩
    const usedPointsInput = document.getElementById("usedPointsInput");
    if (usedPointsInput) {
      usedPointsInput.addEventListener("input", updateOrderSummary);
    }

    // 최초 요약 계산
    updateOrderSummary();
  });
