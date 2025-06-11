function openModal(id) {
    document.getElementById(id).style.display = 'flex';
  }

  function closeModal(id) {
    document.getElementById(id).style.display = 'none';
  }

  window.onclick = function (e) {
    const coupon = document.getElementById('coupon-modal');
    const point = document.getElementById('point-modal');
    if (e.target === coupon) coupon.style.display = 'none';
    if (e.target === point) point.style.display = 'none';
  };