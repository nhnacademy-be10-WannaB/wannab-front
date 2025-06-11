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
  