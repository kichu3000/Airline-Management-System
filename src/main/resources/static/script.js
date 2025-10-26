document.addEventListener('DOMContentLoaded', () => {
    // Initialize Lucide icons
    lucide.createIcons();

    // Mobile menu elements
    const mobileMenu = document.getElementById('mobile-menu');
    const mobileMenuButton = document.getElementById('mobile-menu-button');

    // allNavLinks.forEach(link => {
    //     if (!link) return;
    //     link.addEventListener('click', (e) => {
    //         e.preventDefault();
    //         const pageId = link.dataset.page;
    //         if (pageId) setActivePage(pageId);
    //     });
    // });



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
        const signupModal = document.getElementById('signupModal');
        const closeSignupModalButton = document.getElementById('closeSignupModal');
        const openSignupFromLogin = document.getElementById('openSignupFromLogin'); // the "Sign up" link in login modal

        // Open signup modal from login
        if (openSignupFromLogin) {
            openSignupFromLogin.addEventListener('click', (e) => {
                e.preventDefault();      // prevent default link behavior
                closeLoginModal();       // close login modal
                signupModal.classList.add('show'); // open signup modal
            });
        }

        // Close signup modal
        if (closeSignupModalButton) {
            closeSignupModalButton.addEventListener('click', () => signupModal.classList.remove('show'));
        }

        // Close on backdrop click
        if (signupModal) {
            signupModal.addEventListener('click', (e) => {
                if (e.target === signupModal) signupModal.classList.remove('show');
            });
        }
        // Elements
        const openLoginFromSignup = document.getElementById('openLoginFromSignup'); // "Login" link in signup modal

        // Open login modal from signup
        if (openLoginFromSignup) {
            openLoginFromSignup.addEventListener('click', (e) => {
                e.preventDefault();        // Prevent default link behavior
                signupModal?.classList.remove('show'); // Close signup modal
                loginModal?.classList.add('show');     // Open login modal
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
    // const toastNotification = document.getElementById('toastNotification');
    // const toastMessage = document.getElementById('toastMessage');
    // function showToast(message, type = 'success') {
    //     if (!toastNotification || !toastMessage) return;
    //     toastMessage.textContent = message;
    //     toastNotification.classList.remove('bg-red-500', 'bg-yellow-500', 'bg-green-500', 'bg-sky-500');
    //     if (type === 'success') toastNotification.classList.add('bg-green-500');
    //     else if (type === 'error') toastNotification.classList.add('bg-red-500');
    //     else if (type === 'warning') toastNotification.classList.add('bg-yellow-500');
    //     else if (type === 'info') toastNotification.classList.add('bg-sky-500');

    //     toastNotification.classList.add('show');
    //     setTimeout(() => toastNotification.classList.remove('show'), 3000);
    // }

    // <script th:if="${loginError != null}">
    // showToast([[${loginError}]], 'error');
    // </script>

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
    // const dashboardNavItems = document.querySelectorAll('.dashboard-nav-item');
    // const dashboardContents = document.querySelectorAll('.dashboard-content');
    // dashboardNavItems.forEach(item => {
    //     if (!item) return;
    //     item.addEventListener('click', (e) => {
    //         e.preventDefault();
    //         const targetHref = item.getAttribute('href');
    //         if (!targetHref?.startsWith('#')) return;
    //         const targetId = targetHref.substring(1) + 'Content';
    //         dashboardNavItems.forEach(nav => {
    //             if (!nav) return;
    //             nav.classList.remove('bg-sky-100', 'text-sky-700');
    //             nav.classList.add('text-gray-600');
    //         });
    //         item.classList.add('bg-sky-100', 'text-sky-700');
    //         item.classList.remove('text-gray-600');
    //         dashboardContents.forEach(content => content?.classList.add('hidden'));
    //         const targetContent = document.getElementById(targetId);
    //         if (targetContent) targetContent.classList.remove('hidden');
    //         lucide.createIcons();
    //     });
    // });

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


    
});

document.addEventListener("DOMContentLoaded", () => {
    const seatCheckboxes = document.querySelectorAll('input[name="seat"]');
    const selectedSeatDisplay = document.getElementById("selectedSeatDisplay");

    seatCheckboxes.forEach((checkbox) => {
        checkbox.addEventListener("change", () => {
            const selected = Array.from(seatCheckboxes)
                                .filter(cb => cb.checked)
                                .map(cb => cb.value);
            selectedSeatDisplay.textContent = selected.join(", ") || "None";
        });
    });
});

// Airplane form validation
function validateAirplaneForm() {
const airplaneId = document.getElementById('airplaneId').value.trim();
const model = document.getElementById('model').value.trim();
const capacity = document.getElementById('capacity').value;
const status = document.getElementById('status').value;
if (airplaneId.length < 3) {
alert('Airplane ID must be at least 3 characters');
return false;
}
if (model.length < 3) {
alert('Model name must be at least 3 characters');
return false;
}
if (capacity < 1 || capacity > 1000) {
alert('Capacity must be between 1 and 1000');
return false;
}
if (!status) {
alert('Please select a status');
return false;
}
return true;
}
// Edit airplane function
function editAirplane(id) {
// This would open a modal or redirect to edit page
// For now, you can implement a modal or separate edit page
alert('Edit functionality - ID: ' + id);
}
// Auto-hide success/error messages after 5 seconds
document.addEventListener('DOMContentLoaded', function() {
const alerts = document.querySelectorAll('.alert');
alerts.forEach(alert => {
setTimeout(() => {
alert.style.opacity = '0';
setTimeout(() => alert.remove(), 300);
}, 5000);
});
});

// Enhanced Dashboard Interactions
document.addEventListener('DOMContentLoaded', function() {
    // Initialize Lucide icons
    lucide.createIcons();
    
    // Sidebar toggle for mobile
    const sidebarToggle = document.getElementById('sidebarToggle');
    const sidebar = document.querySelector('.sidebar');
    
    if (sidebarToggle && sidebar) {
        sidebarToggle.addEventListener('click', function() {
            sidebar.classList.toggle('open');
        });
    }
    
    // Close sidebar when clicking outside on mobile
    document.addEventListener('click', function(e) {
        if (window.innerWidth <= 768 && sidebar && !sidebar.contains(e.target) && !sidebarToggle.contains(e.target)) {
            sidebar.classList.remove('open');
        }
    });
    
    // Add fade-in animation to cards
    const cards = document.querySelectorAll('.stats-card, .flight-card, .booking-form');
    cards.forEach((card, index) => {
        card.style.opacity = '0';
        card.style.transform = 'translateY(20px)';
        setTimeout(() => {
            card.style.transition = 'all 0.5s ease';
            card.style.opacity = '1';
            card.style.transform = 'translateY(0)';
        }, index * 100);
    });
    
    // Enhanced form interactions
    const formInputs = document.querySelectorAll('.form-input');
    formInputs.forEach(input => {
        input.addEventListener('focus', function() {
            this.parentElement.classList.add('focused');
        });
        
        input.addEventListener('blur', function() {
            if (!this.value) {
                this.parentElement.classList.remove('focused');
            }
        });
    });
    
    // Real-time search functionality
    const searchInputs = document.querySelectorAll('input[type="search"], input[name="search"]');
    searchInputs.forEach(input => {
        let searchTimeout;
        input.addEventListener('input', function() {
            clearTimeout(searchTimeout);
            searchTimeout = setTimeout(() => {
                performSearch(this.value);
            }, 300);
        });
    });
    
    // Enhanced button interactions
    const buttons = document.querySelectorAll('.btn-primary, .btn-secondary, .action-btn');
    buttons.forEach(button => {
        button.addEventListener('click', function(e) {
            // Add ripple effect
            const ripple = document.createElement('span');
            const rect = this.getBoundingClientRect();
            const size = Math.max(rect.width, rect.height);
            const x = e.clientX - rect.left - size / 2;
            const y = e.clientY - rect.top - size / 2;
            
            ripple.style.width = ripple.style.height = size + 'px';
            ripple.style.left = x + 'px';
            ripple.style.top = y + 'px';
            ripple.classList.add('ripple');
            
            this.appendChild(ripple);
            
            setTimeout(() => {
                ripple.remove();
            }, 600);
        });
    });
    
    // Table row interactions
    const tableRows = document.querySelectorAll('.table-row');
    tableRows.forEach(row => {
        row.addEventListener('mouseenter', function() {
            this.style.transform = 'scale(1.02)';
        });
        
        row.addEventListener('mouseleave', function() {
            this.style.transform = 'scale(1)';
        });
    });
    
    // Loading states for forms
    const forms = document.querySelectorAll('form');
    forms.forEach(form => {
        form.addEventListener('submit', function() {
            const submitBtn = this.querySelector('button[type="submit"]');
            if (submitBtn) {
                submitBtn.classList.add('loading');
                submitBtn.disabled = true;
                submitBtn.innerHTML = '<i data-lucide="loader-2" class="w-4 h-4 animate-spin inline mr-2"></i>Processing...';
                lucide.createIcons();
            }
        });
    });
    
    // Notification system
    window.showNotification = function(message, type = 'info') {
        const notification = document.createElement('div');
        notification.className = `fixed top-4 right-4 p-4 rounded-lg shadow-lg z-50 ${getNotificationClass(type)}`;
        notification.innerHTML = `
            <div class="flex items-center">
                <i data-lucide="${getNotificationIcon(type)}" class="w-5 h-5 mr-2"></i>
                <span>${message}</span>
            </div>
        `;
        
        document.body.appendChild(notification);
        lucide.createIcons();
        
        setTimeout(() => {
            notification.style.opacity = '0';
            notification.style.transform = 'translateX(100%)';
            setTimeout(() => notification.remove(), 300);
        }, 3000);
    };
    
    function getNotificationClass(type) {
        const classes = {
            success: 'bg-green-100 text-green-800 border border-green-200',
            error: 'bg-red-100 text-red-800 border border-red-200',
            warning: 'bg-yellow-100 text-yellow-800 border border-yellow-200',
            info: 'bg-blue-100 text-blue-800 border border-blue-200'
        };
        return classes[type] || classes.info;
    }
    
    function getNotificationIcon(type) {
        const icons = {
            success: 'check-circle',
            error: 'x-circle',
            warning: 'alert-triangle',
            info: 'info'
        };
        return icons[type] || icons.info;
    }
    
    function performSearch(query) {
        // Implement search functionality here
        console.log('Searching for:', query);
    }
});

// Enhanced flight booking functionality
function bookFlight(button) {
    const flightId = button.getAttribute('data-flight-id');
    const flightCard = button.closest('.flight-card');
    
    // Add loading state
    button.innerHTML = '<i data-lucide="loader-2" class="w-4 h-4 animate-spin inline mr-2"></i>Booking...';
    button.disabled = true;
    lucide.createIcons();
    
    // Simulate booking process
    setTimeout(() => {
        if (confirm('Are you sure you want to book this flight?')) {
            // Here you would implement the actual booking logic
            showNotification('Flight booked successfully!', 'success');
            flightCard.style.opacity = '0.5';
            button.innerHTML = 'Booked';
            button.disabled = true;
        } else {
            button.innerHTML = 'Book Now';
            button.disabled = false;
        }
    }, 1000);
}

// Enhanced form validation
function validateForm(form) {
    const inputs = form.querySelectorAll('input[required], select[required], textarea[required]');
    let isValid = true;
    
    inputs.forEach(input => {
        if (!input.value.trim()) {
            input.classList.add('border-red-500');
            isValid = false;
        } else {
            input.classList.remove('border-red-500');
        }
    });
    
    return isValid;
}

// Add ripple effect CSS
const style = document.createElement('style');
style.textContent = `
    .ripple {
        position: absolute;
        border-radius: 50%;
        background: rgba(255, 255, 255, 0.6);
        transform: scale(0);
        animation: ripple-animation 0.6s linear;
        pointer-events: none;
    }
    
    @keyframes ripple-animation {
        to {
            transform: scale(4);
            opacity: 0;
        }
    }
`;
document.head.appendChild(style);
