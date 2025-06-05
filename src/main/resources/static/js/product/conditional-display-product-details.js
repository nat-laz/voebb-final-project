const productTypeSelect = document.querySelector('[name="productTypeId"]');
const bookDetailsBlock = document.getElementById('bookDetailsBlock');
const emediaBlock = document.getElementById('emediaBlock');
const emediaInput = emediaBlock?.querySelector('input');


const typeMap = {
    1: "book",
    2: "ebook",
    3: "dvd",
    4: "boardgame"
};

function toggleConditionalFields() {
    const selectedId = productTypeSelect.value;
    const typeName = typeMap[selectedId];

    const isBook = typeName === "book" || typeName === "ebook";
    const isEbook = typeName === "ebook";

    // Book fields toggle
    bookDetailsBlock.classList.toggle("d-none", !isBook);
    bookDetailsBlock.querySelectorAll("input").forEach(input => {
        input.disabled = !isBook;
        input.required = isBook;
    });

    // eMedia link toggle
    emediaBlock.classList.toggle("d-none", !isEbook);
    if (emediaInput) {
        emediaInput.disabled = !isEbook;
        emediaInput.required = isEbook;
    }
}

productTypeSelect.addEventListener("change", toggleConditionalFields);
toggleConditionalFields();


