document.addEventListener('DOMContentLoaded', () => {
    // Initialize Lucide icons
    lucide.createIcons();

    // Page navigation logic
    const allNavLinks = document.querySelectorAll('.nav-link-custom, .btn-custom[data-page], .footer-nav-link[data-page]');
    const pageSections = document.querySelectorAll('.page-section');
    const mainContent = document.getElementById('mainContent');
    const skeletonLoaderResults = document.getElementById('skeletonLoaderResults');
    const actualFlightResults = document.getElementById('actualFlightResults');

    function setActivePage(pageId, skipScroll = false) {
        pageSections.forEach(section => section?.classList.add('hidden'));
        const activePage = document.getElementById(pageId);
        if (activePage) {
            activePage.classList.remove('hidden');
            if (!skipScroll && mainContent) {
                mainContent.scrollIntoView({ behavior: 'smooth', block: 'start' });
            }

            if (pageId === 'searchResults') {
                if (skeletonLoaderResults) skeletonLoaderResults.style.display = 'block';
                if (actualFlightResults) actualFlightResults.classList.add('hidden');
                setTimeout(() => {
                    if (skeletonLoaderResults) skeletonLoaderResults.style.display = 'none';
                    if (actualFlightResults) actualFlightResults.classList.remove('hidden');
                    lucide.createIcons();
                }, 1500);
            }
        }

        // Update active link styling
        document.querySelectorAll('.nav-link-custom').forEach(link => {
            if (!link) return;
            link.classList.remove('nav-link-active-custom');
            if (link.dataset.page === pageId) link.classList.add('nav-link-active-custom');
        });

        const mobileMenuElement = document.getElementById('mobile-menu');
        if (mobileMenuElement?.classList.contains('block')) {
            toggleMobileMenu();
        }
        lucide.createIcons();
    }

    allNavLinks.forEach(link => {
        if (!link) return;
        link.addEventListener('click', (e) => {
            e.preventDefault();
            const pageId = link.dataset.page;
            if (pageId) setActivePage(pageId);
        });
    });

    setActivePage('home', true); // Initial page load

    // Mobile Menu Toggle
    const mobileMenuButton = document.getElementById('mobileMenuButton');
    const mobileMenu = document.getElementById('mobile-menu');

    function toggleMobileMenu() {
        if (mobileMenu && mobileMenuButton) {
            mobileMenu.classList.toggle('hidden');
            mobileMenu.classList.toggle('block');
            const icons = mobileMenuButton.querySelectorAll('.current-icon');
            icons.forEach(icon => icon.classList.toggle('hidden'));
        }
    }
    if (mobileMenuButton) mobileMenuButton.addEventListener('click', toggleMobileMenu);

    // Login Modal Display (UI only)
    const loginModal = document.getElementById('loginModal');
    const loginModalButton = document.getElementById('loginModalButton');
    const loginModalButtonMobile = document.getElementById('loginModalButtonMobile');
    const closeLoginModalButton = document.getElementById('closeLoginModal');

    function openLoginModal() { loginModal?.classList.add('show'); }
    function closeLoginModal() { loginModal?.classList.remove('show'); }

    if (loginModalButton) loginModalButton.addEventListener('click', openLoginModal);
    if (loginModalButtonMobile) loginModalButtonMobile.addEventListener('click', openLoginModal);
    if (closeLoginModalButton) closeLoginModalButton.addEventListener('click', closeLoginModal);
    if (loginModal) {
        loginModal.addEventListener('click', (e) => {
            if (e.target === loginModal) closeLoginModal();
        });
    }

    // User Profile Dropdown Toggle
    const userMenuButton = document.getElementById('user-menu-button');
    const userProfileDropdown = document.getElementById('userProfileDropdown');
    if (userMenuButton && userProfileDropdown) {
        userMenuButton.addEventListener('click', () => {
            const isExpanded = userMenuButton.getAttribute('aria-expanded') === 'true' || false;
            userMenuButton.setAttribute('aria-expanded', String(!isExpanded));
            userProfileDropdown.classList.toggle('hidden');
        });
        document.addEventListener('click', (event) => {
            if (!userProfileDropdown.classList.contains('hidden') &&
                !userMenuButton.contains(event.target) &&
                !userProfileDropdown.contains(event.target)) {
                userProfileDropdown.classList.add('hidden');
                userMenuButton.setAttribute('aria-expanded', 'false');
            }
        });
    }

    // Toast Notifications
    const toastNotification = document.getElementById('toastNotification');
    const toastMessage = document.getElementById('toastMessage');
    function showToast(message, type = 'success') {
        if (!toastNotification || !toastMessage) return;
        toastMessage.textContent = message;
        toastNotification.classList.remove('bg-red-500', 'bg-yellow-500', 'bg-green-500', 'bg-sky-500');
        if (type === 'success') toastNotification.classList.add('bg-green-500');
        else if (type === 'error') toastNotification.classList.add('bg-red-500');
        else if (type === 'warning') toastNotification.classList.add('bg-yellow-500');
        else if (type === 'info') toastNotification.classList.add('bg-sky-500');

        toastNotification.classList.add('show');
        setTimeout(() => toastNotification.classList.remove('show'), 3000);
    }

    // Seat Selection Logic
    const seatMapContainer = document.getElementById('seatMapContainer');
    const selectedSeatDisplay = document.getElementById('selectedSeatDisplay');
    if (seatMapContainer && selectedSeatDisplay) {
        seatMapContainer.addEventListener('click', (e) => {
            const seat = e.target.closest('.seat-base');
            if (!seat || seat.classList.contains('seat-occupied')) return;
            const currentSelected = seatMapContainer.querySelector('.seat-selected');
            if (currentSelected && currentSelected !== seat) {
                currentSelected.classList.remove('seat-selected');
                currentSelected.classList.add('seat-available');
            }
            seat.classList.toggle('seat-selected');
            seat.classList.toggle('seat-available');
            const newlySelectedSeat = seatMapContainer.querySelector('.seat-selected');
            selectedSeatDisplay.textContent = newlySelectedSeat ? newlySelectedSeat.dataset.seat : 'None';
        });
    }

    // Dashboard Navigation
    const dashboardNavItems = document.querySelectorAll('.dashboard-nav-item');
    const dashboardContents = document.querySelectorAll('.dashboard-content');
    dashboardNavItems.forEach(item => {
        if (!item) return;
        item.addEventListener('click', (e) => {
            e.preventDefault();
            const targetHref = item.getAttribute('href');
            if (!targetHref?.startsWith('#')) return;
            const targetId = targetHref.substring(1) + 'Content';
            dashboardNavItems.forEach(nav => {
                if (!nav) return;
                nav.classList.remove('bg-sky-100', 'text-sky-700');
                nav.classList.add('text-gray-600');
            });
            item.classList.add('bg-sky-100', 'text-sky-700');
            item.classList.remove('text-gray-600');
            dashboardContents.forEach(content => content?.classList.add('hidden'));
            const targetContent = document.getElementById(targetId);
            if (targetContent) targetContent.classList.remove('hidden');
            lucide.createIcons();
        });
    });

    // Admin Panel Navigation
    const adminNavItems = document.querySelectorAll('.admin-nav-item');
    const adminContents = document.querySelectorAll('.admin-content');
    adminNavItems.forEach(item => {
        if (!item) return;
        item.addEventListener('click', (e) => {
            e.preventDefault();
            const targetHref = item.getAttribute('href');
            if (!targetHref?.startsWith('#')) return;
            const targetId = targetHref.substring(1) + 'Content';
            adminNavItems.forEach(nav => {
                if (!nav) return;
                nav.classList.remove('bg-sky-100', 'text-sky-700');
                nav.classList.add('text-gray-600');
            });
            item.classList.add('bg-sky-100', 'text-sky-700');
            item.classList.remove('text-gray-600');
            adminContents.forEach(content => content?.classList.add('hidden'));
            const targetContent = document.getElementById(targetId);
            if (targetContent) targetContent.classList.remove('hidden');
            lucide.createIcons();
        });
    });

    // Set current year in footer
    const currentYearEl = document.getElementById('currentYear');
    if (currentYearEl) currentYearEl.textContent = new Date().getFullYear();

    // Flight Search Form Submission
    const flightSearchForm = document.getElementById('flightSearchForm');
    if (flightSearchForm) {
        flightSearchForm.addEventListener('submit', (e) => {
            e.preventDefault();
            setActivePage('searchResults');
            showToast('Searching for flights...', 'info');
        });
    }
});
