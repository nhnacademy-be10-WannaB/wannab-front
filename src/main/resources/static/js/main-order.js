document.addEventListener('DOMContentLoaded', function () {
  const usedPointsInput = document.getElementById("usedPointsInput");
  const usedPointsDisplay = document.getElementById("usedPointsDisplay");
  const orderCouponSelect = document.getElementById("orderCouponSelect");

  function calculateDiscount(discountValue, discountType, baseAmount) {
    if (discountType === "PERCENT") {
      return Math.floor((baseAmount * discountValue) / 100);
    } else if (discountType === "FIXED") {
      return discountValue;
    }
    return 0;
  }

  function getTotalBookCouponDiscount() {
  let totalDiscount = 0;
  document.querySelectorAll(".book-coupon-select").forEach(select => {
    const selected = select.options[select.selectedIndex];
    const discountValue = parseInt(selected.getAttribute("data-discount-value")) || 0;
    const discountType = selected.getAttribute("data-discount-type");
    const basePrice = parseInt(select.getAttribute("data-base-price")) || 0;

    // ✅ 한 권만 할인 적용
    const perUnitDiscount = calculateDiscount(discountValue, discountType, basePrice);
    totalDiscount += perUnitDiscount;
  });
  return totalDiscount;
}


  function getOrderCouponDiscount() {
    if (!orderCouponSelect) return 0;
    const selected = orderCouponSelect.options[orderCouponSelect.selectedIndex];
    const discountValue = parseInt(selected.getAttribute("data-discount-value")) || 0;
    const discountType = selected.getAttribute("data-discount-type");
    const baseAmount = totalBookPrice;
    return calculateDiscount(discountValue, discountType, baseAmount);
  }

  function updateCouponDiscountDisplay() {
    const totalDiscount = getOrderCouponDiscount() + getTotalBookCouponDiscount();
    const couponDisplay = document.getElementById("couponDiscountDisplay");
    if (couponDisplay) {
      couponDisplay.textContent = "-" + totalDiscount.toLocaleString() + "원";
    }
  }

  function updateWrappingPriceDisplay() {
    let totalWrappingPrice = 0;
    document.querySelectorAll('input[id^="wpPriceInput"]').forEach(input => {
      const price = parseInt(input.value, 10);
      if (!isNaN(price)) totalWrappingPrice += price;
    });
    const wrappingPriceDisplay = document.getElementById('wrappingPriceDisplay');
    if (wrappingPriceDisplay) {
      wrappingPriceDisplay.textContent = "+" + totalWrappingPrice.toLocaleString() + "원";
    }
  }

  function updateFinalAmount() {
    const usedPoints = Math.min(parseInt(usedPointsInput?.value) || 0, parseInt(usedPointsInput?.getAttribute("max")) || 0);
    const wrappingPrice = Array.from(document.querySelectorAll('input[id^="wpPriceInput"]')).reduce((sum, input) => sum + (parseInt(input.value) || 0), 0);
    const totalCouponDiscount = getOrderCouponDiscount() + getTotalBookCouponDiscount();

    let finalAmount = totalBookPrice + shippingFee + wrappingPrice - usedPoints - totalCouponDiscount;
    if (finalAmount < 0) finalAmount = 0;

    const finalAmountDisplay = document.getElementById('finalAmountDisplay');
    if (finalAmountDisplay) {
      finalAmountDisplay.textContent = finalAmount.toLocaleString() + "원";
    }
  }

  document.querySelectorAll('select[data-index]').forEach(selectEl => {
    selectEl.addEventListener('change', function () {
      const selectedOption = this.options[this.selectedIndex];
      const price = selectedOption.getAttribute('data-price');
      const index = this.getAttribute('data-index');
      const hiddenInput = document.getElementById('wpPriceInput' + index);
      hiddenInput.value = price;
      updateWrappingPriceDisplay();
      updateFinalAmount();
    });
  });

  if (usedPointsInput) {
    usedPointsInput.addEventListener("input", function () {
      let usedPoints = parseInt(this.value, 10);
      const maxPoints = parseInt(this.getAttribute("max"), 10);
      if (isNaN(usedPoints) || usedPoints < 0) usedPoints = 0;
      if (!isNaN(maxPoints) && usedPoints > maxPoints) {
        usedPoints = maxPoints;
        this.value = maxPoints;
      }
      usedPointsDisplay.textContent = "-" + usedPoints.toLocaleString() + "원";
      updateFinalAmount();
    });
  }

  if (orderCouponSelect) {
    orderCouponSelect.addEventListener("change", () => {
      updateCouponDiscountDisplay();
      updateFinalAmount();
    });
  }

  document.querySelectorAll(".book-coupon-select").forEach(select => {
    select.addEventListener("change", () => {
      updateCouponDiscountDisplay();
      updateFinalAmount();
    });
  });

  updateWrappingPriceDisplay();
  updateCouponDiscountDisplay();
  updateFinalAmount();
});
