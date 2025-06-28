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

  const paymentButton = document.getElementById('payment-button');
  const orderForm = document.getElementById('order-form');
  const clientKey = document.body.dataset.clientKey;

  paymentButton.addEventListener('click',function (){
    const formData = new FormData(orderForm);

    const xhr = new XMLHttpRequest();

    xhr.open('POST', '/user/main-order/submit', true);

    // 요청이 성공적으로 완료되었을 때 실행될 로직 정의
    xhr.onload = function() {
      // HTTP 상태 코드가 200 (성공)일 경우
      if (xhr.status === 200) {
        // 서버가 보낸 JSON 문자열을 JavaScript 객체로 변환합니다.
        // 이 orderInfo 객체는 Controller가 반환한 OrderInfoForPayment 객체와 내용이 같습니다.
        const orderInfo = JSON.parse(xhr.responseText);
        const tossPayments = TossPayments(clientKey);

        // ✅ 2. tossPayments.payment()를 호출하여 결제 전용 객체를 얻습니다.
        // customerKey는 사용자별로 고유한 값을 사용하는 것이 좋습니다.
        const customerKey = "customer_1" // 예를 들어 userId를 사용
        const payment = tossPayments.payment({ customerKey: customerKey });

        // 변환된 객체 정보를 바탕으로 토스페이먼츠 결제창을 호출합니다.
        payment.requestPayment({
          method: "CARD",
          amount: {
            currency: "KRW",
            value: orderInfo.payAmount
          },
          orderId: 'WannaB' + String(orderInfo.orderId),
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
        })
            .catch(function (error) {
              // 결제창 호출 과정에서 에러 발생 시 (예: 사용자가 창을 닫음)
              if (error.code === 'USER_CANCEL') {
                // 사용자가 결제를 취소했을 때의 처리
                console.log('사용자가 결제를 취소했습니다.');
              } else {
                // 기타 알 수 없는 에러
                alert('결제에 실패하였습니다. 사유: ' + error.message);
              }
            });

      } else {
        alert('주문 생성에 실패했습니다. (재고 부족 등)');
        console.error('Server error:', xhr.status, xhr.statusText);
      }
    };

    xhr.onerror = function() {
      alert('네트워크에 문제가 발생하여 요청을 보낼 수 없습니다.');
    }

    xhr.send(formData);
  });

});
