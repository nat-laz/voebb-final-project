document.addEventListener("DOMContentLoaded", () => {
    validatePhoneInput();
    validateEmailInput();
});

function validatePhoneInput() {
    const phoneInput = document.getElementById('phoneNumber');
    const phoneFeedback = document.getElementById('phoneFeedback');

    const checkPlus = document.getElementById('checkPlus');
    const checkDigits = document.getElementById('checkDigits');
    const checkLength = document.getElementById('checkLength');

    phoneInput.addEventListener('focus', () => {
        phoneFeedback.style.display = 'block';
    });

    phoneInput.addEventListener('blur', () => {
        phoneFeedback.style.display = 'none';
    });

    phoneInput.addEventListener('input', function () {
        const value = phoneInput.value;

        const startsWithPlus = value.startsWith('+');
        const digitsOnly = /^\+?\d+$/.test(value);
        const digitCount = value.length > 12 && value.length < 15;

        // Update checklist
        if (startsWithPlus) {
            checkPlus.textContent = '✅ Starts with a "+" sign';
            checkPlus.classList.add("text-success");
        } else {
            checkPlus.textContent = '❌ Starts with a "+" sign';
            checkPlus.classList.remove("text-success");
        }

        if (digitsOnly) {
            checkDigits.textContent = '✅ Only contains numbers after "+" sign';
            checkDigits.classList.add("text-success");
        } else {
            checkDigits.textContent = '❌ Only contains numbers after "+" sign';
            checkDigits.classList.remove("text-success");
        }

        // Update checklist
        if (digitCount) {
            checkLength.textContent = '✅ 11 or 12 digits';
            checkLength.classList.add("text-success");
        } else {
            checkLength.textContent = '❌ 11 or 12 digits';
            checkLength.classList.remove("text-success");
        }

        if (startsWithPlus && digitsOnly && digitCount) {
            phoneInput.classList.add('is-valid');
            phoneInput.classList.remove('is-invalid');
        } else {
            phoneInput.classList.add('is-invalid');
            phoneInput.classList.remove('is-valid');
        }
    });
}

function validateEmailInput() {
    const emailInput = document.getElementById('email');

    emailInput.addEventListener('input', () => {
        const emailValue = emailInput.value;

        const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

        if (re.test(emailValue)) {
            emailInput.classList.add('is-valid');
            emailInput.classList.remove('is-invalid');
        } else {
            emailInput.classList.add('is-invalid');
            emailInput.classList.remove('is-valid');
        }
    });
}


