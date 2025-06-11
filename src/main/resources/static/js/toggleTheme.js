const html = document.documentElement;
const body = document.body;

const savedTheme = localStorage.getItem("theme") || "light";
html.setAttribute("data-bs-theme", savedTheme);
body.setAttribute("data-theme", savedTheme);

const updateThemeIcon = (theme) => {
    const icon = document.getElementById('themeIcon');
    const iconMobile = document.getElementById('themeIconMobile');

    const applyIconFlip = (el) => {
        if (theme === 'light') {
            el?.classList.add('icon-flip');
        } else {
            el?.classList.remove('icon-flip');
        }
    };

    applyIconFlip(icon);
    applyIconFlip(iconMobile);
};

const toggleTheme = () => {
    const isDark = html.getAttribute('data-bs-theme') === 'dark';
    const newTheme = isDark ? 'light' : 'dark';

    html.setAttribute('data-bs-theme', newTheme);
    body.setAttribute('data-theme', newTheme);

    localStorage.setItem("theme", newTheme);
    updateThemeIcon(newTheme);
};

document.getElementById('themeToggle')?.addEventListener('click', toggleTheme);
document.getElementById('themeToggleMobile')?.addEventListener('click', toggleTheme);

document.addEventListener('DOMContentLoaded', () => {
    updateThemeIcon(savedTheme);
});