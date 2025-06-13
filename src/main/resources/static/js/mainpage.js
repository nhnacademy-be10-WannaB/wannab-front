/// 모달 열고 닫는 함수
function openModal(id) {
    document.getElementById(id).style.display = 'flex';
}

function closeModal(id) {
    document.getElementById(id).style.display = 'none';
}

// 모달밖 화면 클릭시 모달 닫히는 함수
window.onclick = function (e) {
    const orderModal = document.getElementById('non-member-order-modal');
    if (e.target === orderModal) orderModal.style.display = 'none';
};