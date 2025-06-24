document.addEventListener('DOMContentLoaded', function() {
  // 포장지 선택 시 가격 반영
  const selectElements = document.querySelectorAll('select[data-index]');

  selectElements.forEach(selectEl => {
    selectEl.addEventListener('change', function() {
      const selectedOption = this.options[this.selectedIndex];
      const price = selectedOption.getAttribute('data-price');
      const index = this.getAttribute('data-index');
      const hiddenInput = document.getElementById('wpPriceInput' + index);

      hiddenInput.value = price;

      // 포장지 전체 합산 금액 업데이트
      updateWrappingPriceDisplay();
    });
  });

  // 2포인트 입력 시 표시 업데이트
  const usedPointsInput = document.getElementById("usedPointsInput");
  const usedPointsDisplay = document.getElementById("usedPointsDisplay");

  if (usedPointsInput) {
    usedPointsInput.addEventListener("input", function() {
      let usedPoints = parseInt(this.value, 10);
      if (isNaN(usedPoints) || usedPoints < 0) usedPoints = 0;

      const maxPoints = parseInt(this.getAttribute("max"), 10);
      if (!isNaN(maxPoints) && usedPoints > maxPoints) {
        usedPoints = maxPoints;
        this.value = maxPoints;
      }

      if (usedPointsDisplay) {
        usedPointsDisplay.textContent = "-" + usedPoints.toLocaleString() + "원";
      }

      updateFinalAmount();
    });
  }

  // 포장지 합산 가격 표시
  function updateWrappingPriceDisplay() {
    let totalWrappingPrice = 0;

    const hiddenInputs = document.querySelectorAll('input[id^="wpPriceInput"]');
    hiddenInputs.forEach(input => {
      const price = parseInt(input.value, 10);
      if (!isNaN(price)) {
        totalWrappingPrice += price;
      }
    });

    const wrappingPriceDisplay = document.getElementById('wrappingPriceDisplay');
    if (wrappingPriceDisplay) {
      wrappingPriceDisplay.textContent = "+" + totalWrappingPrice.toLocaleString() + "원";
    }

    updateFinalAmount();
  }

  // 최종 결제 금액 업데이트
  function updateFinalAmount() {
    // 현재 포인트 사용 금액
    let usedPoints = 0;
    if (usedPointsInput) {
      usedPoints = parseInt(usedPointsInput.value, 10);
      if (isNaN(usedPoints) || usedPoints < 0) usedPoints = 0;
    }

    // 현재 포장지 가격 총합
    let totalWrappingPrice = 0;
    const hiddenInputs = document.querySelectorAll('input[id^="wpPriceInput"]');
    hiddenInputs.forEach(input => {
      const price = parseInt(input.value, 10);
      if (!isNaN(price)) {
        totalWrappingPrice += price;
      }
    });

    // 계산
    console.log(totalBookPrice,shippingFee,totalWrappingPrice,usedPoints)
    let finalAmount = totalBookPrice + shippingFee + totalWrappingPrice - usedPoints;
    if (finalAmount < 0) finalAmount = 0;

    // 표시 업데이트
    const finalAmountDisplay = document.getElementById('finalAmountDisplay');
    if (finalAmountDisplay) {
      finalAmountDisplay.textContent = finalAmount.toLocaleString() + "원";
    }
  }

  updateWrappingPriceDisplay();
});
