/// 모달 열고 닫는 함수
function openModal(id) {
    document.getElementById(id).style.display = 'flex';
  }

function closeModal(id) {
  document.getElementById(id).style.display = 'none';
}

// 모달밖 화면 클릭시 모달 닫히는 함수
window.onclick = function (e) {
  const couponModal = document.getElementById('coupon-modal');
  const pointModal = document.getElementById('point-modal');
  const orderModal = document.getElementById('order-modal');
  const reviewModal = document.getElementById('review-modal');
  const editReviewModal = document.getElementById('edit-review-modal');

  if (e.target === couponModal) couponModal.style.display = 'none';
  if (e.target === pointModal) pointModal.style.display = 'none';
  if (e.target === orderModal) orderModal.style.display = "none";
  if (e.target === reviewModal) reviewModal.style.display = "none";
  if (e.target === editReviewModal) editReviewModal.style.display= "none";

};