document.addEventListener("DOMContentLoaded", () => {
    const track = document.querySelector('.carousel-track');
    const visibleCards = 3;
    let cardWidth;
    let currentIndex;
    let isTransitioning = false;

    // Wait for all images to load before calculating width
    window.addEventListener("load", () => {
        const originalCards = Array.from(track.children);

        if (originalCards.length === 0) {
            console.error("No cards found in carousel-track.");
            return;
        }

        cardWidth = originalCards[0].offsetWidth + 16;
        console.log("Card width:", cardWidth); // Debug

        currentIndex = visibleCards;

        const prepend = originalCards.slice(-visibleCards).map(card => card.cloneNode(true));
        const append = originalCards.slice(0, visibleCards).map(card => card.cloneNode(true));

        prepend.forEach(card => track.insertBefore(card, track.firstChild));
        append.forEach(card => track.appendChild(card));

        updatePosition(false);

        document.querySelector('.nav.left').addEventListener('click', () => move(-1));
        document.querySelector('.nav.right').addEventListener('click', () => move(1));
    });

    function updatePosition(transition = true) {
        track.style.transition = transition ? 'transform 0.5s ease' : 'none';
        track.style.transform = `translateX(-${currentIndex * cardWidth}px)`;
    }

    function move(direction) {
        if (isTransitioning) return;
        isTransitioning = true;
        currentIndex += direction * visibleCards;
        updatePosition(true);
    }

    track.addEventListener('transitionend', () => {
        const total = track.children.length;
        const realCount = total - visibleCards * 2;

        if (currentIndex >= realCount + visibleCards) {
            currentIndex = visibleCards;
            updatePosition(false);
        } else if (currentIndex < visibleCards) {
            currentIndex = realCount;
            updatePosition(false);
        }
        isTransitioning = false;
    });
});
