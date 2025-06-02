// Change URL
const tabLinks = document.querySelectorAll('a[data-bs-toggle="tab"]');
tabLinks.forEach(tab => {
    tab.addEventListener('shown.bs.tab', function (e) {
        const hash = e.target.getAttribute('href');
        history.replaceState(null, null, hash);
    });
});

// Activate tab based on URL
window.addEventListener('DOMContentLoaded', () => {
    const hash = window.location.hash;
    if (hash) {
        const tab = document.querySelector(`a[href="${hash}"]`);
        if (tab) {
            const tabTrigger = new bootstrap.Tab(tab);
            tabTrigger.show();
        }
    }
});