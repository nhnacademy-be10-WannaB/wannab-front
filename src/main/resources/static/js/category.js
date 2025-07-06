document.addEventListener('DOMContentLoaded', function() {
    const toggleBtn = document.querySelector('.category-toggle-btn');
    const closeBtn = document.querySelector('.category-close-btn');
    const sidebar = document.querySelector('.category-sidebar');
    const overlay = document.querySelector('.sidebar-overlay');

    function openMenu() {
        sidebar.classList.add('is-open');
        overlay.classList.add('is-active');
        if(toggleBtn) toggleBtn.classList.add('is-hidden');
    }

    function closeMenu() {
        sidebar.classList.remove('is-open');
        overlay.classList.remove('is-active');
        if(toggleBtn) toggleBtn.classList.remove('is-hidden');
    }

    if(toggleBtn) toggleBtn.addEventListener('click', openMenu);
    if(closeBtn) closeBtn.addEventListener('click', closeMenu);
    if(overlay) overlay.addEventListener('click', closeMenu);

    // 딜레이 기능 코드
    const menuItems = document.querySelectorAll('.main-menu > li');
    let hoverTimer;

    menuItems.forEach(item => {
        const submenu = item.querySelector('.submenu');
        if (submenu) {
            item.addEventListener('mouseenter', () => {
                clearTimeout(hoverTimer);
                hoverTimer = setTimeout(() => {
                    submenu.classList.add('is-open');
                }, 200);
            });
            item.addEventListener('mouseleave', () => {
                clearTimeout(hoverTimer);
                submenu.classList.remove('is-open');
            });
        }
    });
});