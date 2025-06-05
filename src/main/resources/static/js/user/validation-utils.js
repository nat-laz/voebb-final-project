function isPhoneValid(value) {
    const startsWithPlus = /(^\+)/.test(value);
    const startsWithZero = /(^0)/.test(value);
    const digitsOnly = /^\+?\d+$/.test(value);
    const digitCountInternational = value.length >= 13 && value.length <= 14;
    const digitCountLocal = value.length >= 11 && value.length <= 12;

    const isValid = (startsWithPlus || startsWithZero) && digitsOnly && (digitCountInternational || digitCountLocal);

    return {
        isValid,
        startsWithPlus,
        startsWithZero,
        digitsOnly,
        digitCountInternational,
        digitCountLocal,
    };
}

function updateRuleCheck(element, passed, message) {
    if (!element) return;
    element.textContent = `${passed ? '✅' : '❌'} ${message}`;
    element.classList.toggle('text-success', passed);
    element.classList.toggle('text-danger', !passed);
}

function validatePhoneInputWithFeedback(inputId, feedbackId) {
    const phoneInput = document.getElementById(inputId);
    const phoneFeedback = document.getElementById(feedbackId);

    const checkPlus = document.getElementById('checkPlus');
    const checkDigits = document.getElementById('checkDigits');
    const checkLength = document.getElementById('checkLength');

    if (!phoneInput || !phoneFeedback || !checkPlus || !checkDigits || !checkLength) return;

    phoneInput.addEventListener('focus', () => phoneFeedback.style.display = 'block');
    phoneInput.addEventListener('blur', () => phoneFeedback.style.display = 'none');

    phoneInput.addEventListener('input', function () {
        let value = phoneInput.value.replace(/[^0-9+]/g, '');
        phoneInput.value = value;

        const results = isPhoneValid(value);

        if (results.startsWithPlus) {
            phoneInput.removeAttribute('maxLength');
            phoneInput.setAttribute('maxLength', '14');
        } else if (results.startsWithZero) {
            phoneInput.removeAttribute('maxLength');
            phoneInput.setAttribute('maxLength', '12');
        }

        if (checkPlus) {
            updateRuleCheck(checkPlus, results.startsWithPlus || results.startsWithZero, 'Starts with zero or a "+"');
        }

        if (checkDigits) {
            updateRuleCheck(checkDigits, results.digitsOnly, 'Contains only numbers');
        }

        if (checkLength) {
            const validLength = results.digitCountInternational || results.digitCountLocal;
            updateRuleCheck(checkLength, validLength, 'Valid number length');
        }

        highlightInput(results.isValid, phoneInput);
    });

}

function validateEmailInput(inputId) {
    const emailInput = document.getElementById(inputId);

    emailInput.addEventListener('input', () => {
        const emailValue = emailInput.value;

        const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

        highlightInput(re.test(emailValue), emailInput)
    });
}

function checkPasswordStrengthAdvanced(passwordInputId, feedbackId, lengthCheckId, digitCheckId, specialCheckId, letterCheckId) {
    const passwordInput = document.getElementById(passwordInputId);
    const feedback = document.getElementById(feedbackId);

    const checkLength = document.getElementById(lengthCheckId);
    const checkDigit = document.getElementById(digitCheckId);
    const checkSpecial = document.getElementById(specialCheckId);
    const checkLetter = document.getElementById(letterCheckId);

    if (!passwordInput || !feedback || !checkLength || !checkDigit || !checkSpecial || !checkLetter) return;

    passwordInput.addEventListener('focus', () => {
        feedback.style.display = 'block';
    });

    passwordInput.addEventListener('blur', () => {
        feedback.style.display = 'none';
    });

    passwordInput.addEventListener('input', () => {
        const value = passwordInput.value;

        const rules = {
            length: value.length >= 8,
            digit: /\d/.test(value),
            letter: /[a-zA-Z]/.test(value),
            special: /[^a-zA-Z0-9]/.test(value),
        };

        updateRuleCheck(checkLength, rules.length, 'Minimum 8 characters');
        updateRuleCheck(checkDigit, rules.digit, 'At least 1 digit');
        updateRuleCheck(checkSpecial, rules.special, 'At least 1 special character');
        updateRuleCheck(checkLetter, rules.letter, 'At least 1 letter');

        const isStrong = Object.values(rules).every(Boolean);
        highlightInput(isStrong, passwordInput);
    });
}

function arePasswordsMatching(passwordInputId, confirmPasswordId, feedbackId) {
    const passwordInput = document.getElementById(passwordInputId);
    const confirmInput = document.getElementById(confirmPasswordId);
    const feedback = document.getElementById(feedbackId);

    if (!passwordInput || !confirmInput || !feedback) return;

    const updateMatchStatus = () => {
        const password = passwordInput.value.trim();
        const confirm = confirmInput.value.trim();

        if (!confirm) {
            resetValidation();
            return;
        }

        const match = password === confirm;
        highlightInput(match, confirmInput);

        feedback.style.display = match ? 'none' : 'block';
    };

    const resetValidation = () => {
        confirmInput.classList.remove('is-valid', 'is-invalid');
        feedback.style.display = 'none';
    };

    passwordInput.addEventListener('input', updateMatchStatus);
    confirmInput.addEventListener('input', updateMatchStatus);
}


function validateNames(firstNameId, lastNameId) {
    const firstName = document.getElementById(firstNameId);
    const lastName = document.getElementById(lastNameId);

    firstName.addEventListener('input', () => {
        highlightInput(firstName.value.length > 0, firstName);
    })

    lastName.addEventListener('input', () => {
        highlightInput(lastName.value.length > 0, lastName);
    })

}

function highlightInput(isValidCondition, inputField) {
    const value = inputField.value.trim();

    if (!value) {
        inputField.classList.remove('is-valid', 'is-invalid');
        return;
    }

    inputField.classList.toggle('is-valid', isValidCondition);
    inputField.classList.toggle('is-invalid', !isValidCondition);
}