document.addEventListener("DOMContentLoaded", () => {
    checkNames();
    validatePhoneInput();
    validateEmailInput();
    checkPasswordStrength();
    arePasswordsMatching();
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

function checkPasswordStrength(){
    const passwordInput = document.getElementById('registerPassword');
    const phoneFeedback = document.getElementById('passwordFeedback');

    const checkLength = document.getElementById('passwordLengthCheck');
    const checkDigit = document.getElementById('passwordDigitCheck');
    const checkSpecial = document.getElementById('PasswordSpecialCharsCheck');
    const checkLetter = document.getElementById('PasswordLetterCheck');

    passwordInput.addEventListener('focus', () => {
        phoneFeedback.style.display = 'block';
    });

    passwordInput.addEventListener('blur', () => {
        phoneFeedback.style.display = 'none';
    });

    passwordInput.addEventListener('input', function () {
        const value = passwordInput.value;

        const length = value.length > 7;
        const digit = /\d/.test(value);
        const letter = /[a-zA-Z]/.test(value);
        const special = /[^a-zA-Z0-9]/.test(value);

        // Update checklist
        if (length) {
            checkLength.textContent = '✅ Minimum 8 chars';
            checkLength.classList.add("text-success");
        } else {
            checkLength.textContent = '❌ Minimum 8 chars';
            checkLength.classList.remove("text-success");
        }

        if (digit) {
            checkDigit.textContent = '✅ At least 1 digit';
            checkDigit.classList.add("text-success");
        } else {
            checkDigit.textContent = '❌ At least 1 digit';
            checkDigit.classList.remove("text-success");
        }

        if (special) {
            checkSpecial.textContent = '✅ At least 1 special char';
            checkSpecial.classList.add("text-success");
        } else {
            checkSpecial.textContent = '❌ At least 1 special char';
            checkSpecial.classList.remove("text-success");
        }

        if (letter) {
            checkLetter.textContent = '✅ At least 1 letter';
            checkLetter.classList.add("text-success");
        } else {
            checkLetter.textContent = '❌ At least 1 letter';
            checkLetter.classList.remove("text-success");
        }

        if (length && digit && special && letter) {
            passwordInput.classList.add('is-valid');
            passwordInput.classList.remove('is-invalid');
        } else {
            passwordInput.classList.add('is-invalid');
            passwordInput.classList.remove('is-valid');
        }
    });
}

function arePasswordsMatching(){
    const passwordInput = document.getElementById('registerPassword');
    const confirmInput = document.getElementById('confirmPassword');
    const feedback = document.getElementById('passwordMatchFeedback');

    function checkPasswordsMatch() {
        const password = passwordInput.value;
        const confirm = confirmInput.value;

        if (confirm === '') {
            confirmInput.classList.remove('is-valid', 'is-invalid');
            feedback.style.display = 'none';
            return;
        }

        if (password === confirm) {
            confirmInput.classList.add('is-valid');
            confirmInput.classList.remove('is-invalid');
            feedback.style.display = 'none';
        } else {
            confirmInput.classList.add('is-invalid');
            confirmInput.classList.remove('is-valid');
            feedback.style.display = 'block';
        }
    }

    passwordInput.addEventListener('input', checkPasswordsMatch);
    confirmInput.addEventListener('input', checkPasswordsMatch);
}

function checkNames(){
    const firstName = document.getElementById('firstName');
    const lastName = document.getElementById('lastName');

    firstName.addEventListener('input', () => {
        highlightInput(firstName.value.length > 0, firstName);
    })

    lastName.addEventListener('input', () => {
        highlightInput(lastName.value.length > 0, lastName);
    })

}

function highlightInput(isValid, input){
    if(input.value.length < 1){
        input.classList.remove('is-invalid', 'is-valid');
        return;
    }
    if (isValid) {
        input.classList.add('is-valid');
        input.classList.remove('is-invalid');
    } else {
        input.classList.add('is-invalid');
        input.classList.remove('is-valid');
    }
}



