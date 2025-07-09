document.addEventListener('DOMContentLoaded', function () {
  const usedPointsInput = document.getElementById("usedPointsInput");
  const usedPointsDisplay = document.getElementById("usedPointsDisplay");
  const orderCouponSelect = document.getElementById("orderCouponSelect");
  const finalAmountDisplay = document.getElementById('finalAmountDisplay');
  const wrappingPriceDisplay = document.getElementById('wrappingPriceDisplay');
  const paymentButton = document.getElementById('payment-button');
  const orderForm = document.getElementById('order-form');
  const clientKey = document.body.dataset.clientKey;

  function calculateDiscount(discountValue, discountType, baseAmount) {
    if (discountType === "PERCENT") return Math.floor((baseAmount * discountValue) / 100);
    if (discountType === "FIXED") return discountValue;
    return 0;
  }

  function getTotalBookCouponDiscount() {
    let totalDiscount = 0;
    document.querySelectorAll(".book-coupon-select").forEach(select => {
      const selected = select.options[select.selectedIndex];
      const discountValue = parseInt(selected.getAttribute("data-discount-value")) || 0;
      const discountType = selected.getAttribute("data-discount-type");
      const basePrice = parseInt(select.getAttribute("data-base-price")) || 0;
      totalDiscount += calculateDiscount(discountValue, discountType, basePrice);
    });
    return totalDiscount;
  }

  function getOrderCouponDiscount() {
    if (!orderCouponSelect) return 0;
    const selected = orderCouponSelect.options[orderCouponSelect.selectedIndex];
    const discountValue = parseInt(selected.getAttribute("data-discount-value")) || 0;
    const discountType = selected.getAttribute("data-discount-type");
    return calculateDiscount(discountValue, discountType, totalBookPrice);
  }

  function updateWrappingPriceDisplay() {
    let totalWrappingPrice = 0;
    document.querySelectorAll('input[id^="wpPriceInput"]').forEach(input => {
      const price = parseInt(input.value, 10);
      if (!isNaN(price)) totalWrappingPrice += price;
    });
    if (wrappingPriceDisplay) {
      wrappingPriceDisplay.textContent = "+" + totalWrappingPrice.toLocaleString() + "원";
    }
  }

  function updateCouponDiscountDisplay() {
    const totalDiscount = getOrderCouponDiscount() + getTotalBookCouponDiscount();
    const couponDisplay = document.getElementById("couponDiscountDisplay");
    if (couponDisplay) {
      couponDisplay.textContent = "-" + totalDiscount.toLocaleString() + "원";
    }
  }

  function updateFinalAmount() {
    const usedPoints = Math.min(parseInt(usedPointsInput?.value) || 0, parseInt(usedPointsInput?.getAttribute("max")) || 0);
    const wrappingPrice = Array.from(document.querySelectorAll('input[id^="wpPriceInput"]')).reduce((sum, input) => {
      const val = parseInt(input.value);
      return sum + (isNaN(val) ? 0 : val);
    }, 0);
    const totalCouponDiscount = getOrderCouponDiscount() + getTotalBookCouponDiscount();

    let finalAmount = totalBookPrice + shippingFee + wrappingPrice - usedPoints - totalCouponDiscount;
    if (finalAmount < 0) finalAmount = 0;

    if (finalAmountDisplay) {
      finalAmountDisplay.textContent = finalAmount.toLocaleString() + "원";
    }
  }

  // ✅ 포장지 선택 이벤트
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

  // ✅ 포인트 사용 입력
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

  // ✅ 쿠폰 선택 이벤트
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

  // ✅ 배송지 select + hidden 연동
  const addressSelect = document.getElementById("addressSelect");
  const recipientAddressHidden = document.getElementById("recipientAddressHidden");
  const manualAddressInput = document.getElementById("manualAddressInput");

  if (addressSelect && recipientAddressHidden && manualAddressInput) {
    addressSelect.addEventListener("change", function () {
      const selectedValue = this.value;
      if (selectedValue === "") {
        // 직접 입력 모드
        manualAddressInput.style.display = "inline";
        recipientAddressHidden.value = "";
        manualAddressInput.addEventListener("input", function () {
          recipientAddressHidden.value = this.value;
        });
      } else {
        // 저장된 주소 선택
        manualAddressInput.style.display = "none";
        recipientAddressHidden.value = selectedValue;
      }
    });
  }

  // ✅ 초기 렌더링 시 계산
  updateWrappingPriceDisplay();
  updateCouponDiscountDisplay();
  updateFinalAmount();

  // ✅ 결제 버튼 클릭
  paymentButton?.addEventListener('click', function () {
    const formData = new FormData(orderForm);
    const xhr = new XMLHttpRequest();
    xhr.open('POST', '/user/main-order/submit', true);

    xhr.onload = function () {
      if (xhr.status === 200) {
        const orderInfo = JSON.parse(xhr.responseText);
        const tossPayments = TossPayments(clientKey);
        const payment = tossPayments.payment({ customerKey: "customer_1" });

        payment.requestPayment({
          method: "CARD",
          amount: { currency: "KRW", value: orderInfo.payAmount },
          orderId: 'testWannaB' + String(orderInfo.orderId),
          orderName: orderInfo.orderName,
          successUrl: window.location.origin + "/user/payment/success",
          failUrl: window.location.origin + "/user/payment/fail",
          customerEmail: "",
          customerName: "1",
          card: {
            useEscrow: false,
            flowMode: "DEFAULT",
            useCardPoint: false,
            useAppCardOnly: false,
          },
        }).catch(function (error) {
          if (error.code === 'USER_CANCEL') {
            console.log('사용자가 결제를 취소했습니다.');
          } else {
            alert('결제에 실패하였습니다. 사유: ' + error.message);
          }
        });
      } else {
        alert('주문 생성에 실패했습니다. (재고 부족 등)');
        console.error('Server error:', xhr.status, xhr.statusText);
      }
    };

    xhr.onerror = function () {
      alert('네트워크에 문제가 발생하여 요청을 보낼 수 없습니다.');
    };

    xhr.send(formData);
  });
});
