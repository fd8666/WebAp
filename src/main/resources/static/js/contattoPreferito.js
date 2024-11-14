document.addEventListener('DOMContentLoaded', function() {

    const emailContact = document.getElementById('emailContact');
    const phoneContact = document.getElementById('phoneContact');
    const phoneField = document.getElementById('phoneField');
    phoneContact.addEventListener('change', function() {

        if (phoneContact.checked) {

            phoneField.style.display = 'block';

        }

    });

    emailContact.addEventListener('change', function() {

        if (emailContact.checked) {

            phoneField.style.display = 'none';

        }

    });

});