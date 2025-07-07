document.addEventListener('DOMContentLoaded', function() {
    // 사이드바 열고 닫는 로직
    const toggleBtn = document.querySelector('.category-toggle-btn');
    const closeBtn = document.querySelector('.category-close-btn');
    const sidebar = document.querySelector('.category-sidebar');
    const overlay = document.querySelector('.sidebar-overlay');

    function openMenu() {
        if (sidebar) sidebar.classList.add('is-open');
        if (overlay) overlay.classList.add('is-active');
        if (toggleBtn) toggleBtn.classList.add('is-hidden');
    }

    function closeMenu() {
        if (sidebar) sidebar.classList.remove('is-open');
        if (overlay) overlay.classList.remove('is-active');
        if (toggleBtn) toggleBtn.classList.remove('is-hidden');
    }

    if (toggleBtn) toggleBtn.addEventListener('click', openMenu);
    if (closeBtn) closeBtn.addEventListener('click', closeMenu);
    if (overlay) overlay.addEventListener('click', closeMenu);

    // 아코디언 메뉴 클릭 로직
    const submenuToggleButtons = document.querySelectorAll('.submenu-toggle-btn');

    submenuToggleButtons.forEach(button => {
        button.addEventListener('click', (event) => {
            event.stopPropagation(); // 이벤트 버블링 방지

            const parentLinkDiv = button.parentElement;
            const submenu = parentLinkDiv.nextElementSibling;

            if (submenu && submenu.classList.contains('submenu')) {
                parentLinkDiv.classList.toggle('is-open');
                submenu.classList.toggle('is-open');
            }
        });
    });
});