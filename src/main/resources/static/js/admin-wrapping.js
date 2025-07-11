function openEditForm(button) {
    const id = button.getAttribute('data-id');
    const name = button.getAttribute('data-name');
    const price = button.getAttribute('data-price');

    document.getElementById('edit-id').value = id;
    document.getElementById('edit-name').value = name;
    document.getElementById('edit-price').value = price;

    document.getElementById('edit-form-container').classList.remove('hidden');
    window.scrollTo({
        top: document.getElementById('edit-form-container').offsetTop - 50,
        behavior: 'smooth'
    });
}

function closeEditForm() {
    document.getElementById('edit-form-container').classList.add('hidden');
}