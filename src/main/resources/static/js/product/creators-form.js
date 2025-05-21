const searchInput = document.getElementById("searchCreator");
const resultsBox = document.getElementById("searchResults");

searchInput.addEventListener("blur", function () {
    setTimeout(() => resultsBox.classList.add("d-none"), 200);
});

searchInput.addEventListener("input", async function () {
    const query = this.value.trim();
    resultsBox.innerHTML = "";

    if (!query) {
        resultsBox.classList.add("d-none");
        return;
    }

    try {
        const res = await fetch(`/api/creators/search?lastName=${encodeURIComponent(query)}`);
        const creators = await res.json();

        if (creators.length > 0) {
            creators.forEach(c => {
                const li = document.createElement("li");
                li.className = "list-group-item list-group-item-action";
                li.textContent = `${c.firstName} ${c.lastName}`;
                li.onclick = () => {
                    document.getElementById("creatorId").value = c.id;
                    document.getElementById("creatorFirstName").value = c.firstName;
                    document.getElementById("creatorLastName").value = c.lastName;
                    searchInput.value = `${c.firstName} ${c.lastName}`;
                    resultsBox.innerHTML = "";
                    resultsBox.classList.add("d-none");
                };
                resultsBox.appendChild(li);
            });
        } else {
            const li = document.createElement("li");
            li.className = "list-group-item d-flex justify-content-between align-items-center text-muted";
            li.innerHTML = `
                    <span>No match found.</span>
                    <button type="button" class="btn btn-sm btn-outline-primary" onclick="openNewCreatorModal()">Add New</button>
                `;
            resultsBox.appendChild(li);
        }

        resultsBox.classList.remove("d-none");

    } catch (error) {
        console.error("Search failed:", error);
        resultsBox.classList.add("d-none");
    }
});

function addCreatorToPreview() {
    const roleInput = document.getElementById("creatorRole");
    const nameInput = document.getElementById("searchCreator");

    const role = roleInput.value.trim();
    const id = document.getElementById("creatorId").value;
    const firstName = document.getElementById("creatorFirstName").value;
    const lastName = document.getElementById("creatorLastName").value;
    const index = parseInt(document.getElementById("creatorIndex").value);

    if (!role || !firstName || !lastName) {
        alert("Please select a creator and provide a role.");
        return;
    }

    const wrapper = document.createElement("div");
    wrapper.className = "selected-tag";
    wrapper.innerHTML = `
            ${firstName} ${lastName} — ${role}
            <button type="button" onclick="this.parentElement.remove()">×</button>
            <input type="hidden" name="creators[${index}].id" value="${id}">
            <input type="hidden" name="creators[${index}].firstName" value="${firstName}">
            <input type="hidden" name="creators[${index}].lastName" value="${lastName}">
            <input type="hidden" name="creators[${index}].role" value="${role}">
        `;

    document.getElementById("creatorsPreview").appendChild(wrapper);

    nameInput.value = "";
    roleInput.value = "";
    document.getElementById("creatorId").value = "";
    document.getElementById("creatorFirstName").value = "";
    document.getElementById("creatorLastName").value = "";
    document.getElementById("creatorIndex").value = index + 1;

    nameInput.removeAttribute("required");
    roleInput.removeAttribute("required");
}

let activeCreatorIndex = null;

function openNewCreatorModal() {
    activeCreatorIndex = "modal";
    document.getElementById("newFirstName").value = "";
    document.getElementById("newLastName").value = "";
    new bootstrap.Modal(document.getElementById("newCreatorModal")).show();
}

document.getElementById("newCreatorForm").addEventListener("submit", function (e) {
    e.preventDefault();
    const fn = document.getElementById("newFirstName").value.trim();
    const ln = document.getElementById("newLastName").value.trim();

    if (!fn || !ln) return;

    document.getElementById("creatorId").value = "";
    document.getElementById("creatorFirstName").value = fn;
    document.getElementById("creatorLastName").value = ln;
    document.getElementById("searchCreator").value = `${fn} ${ln}`;

    bootstrap.Modal.getInstance(document.getElementById("newCreatorModal")).hide();
});

// Warning pop-up: At least one creator should be added before submitting
document.addEventListener('DOMContentLoaded', function () {
    const form = document.querySelector('.needs-validation');
    form.addEventListener('submit', function (event) {
        const creatorFields = document.querySelectorAll('#creatorsPreview input[type="hidden"]');
        if (creatorFields.length === 0) {
            event.preventDefault();
            event.stopPropagation();
            alert("Please add at least one creator before submitting.");
        }
    });
});