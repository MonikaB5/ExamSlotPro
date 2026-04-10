const API_BASE = '/api';

// --- UI Navigation ---
function showSection(sectionId) {
    document.querySelectorAll('.nav-links li').forEach(li => li.classList.remove('active'));
    event.target.classList.add('active');

    document.querySelectorAll('section').forEach(sec => sec.classList.add('hidden'));
    document.getElementById(`${sectionId}-section`).classList.remove('hidden');

    const titles = {
        'users': 'User Management',
        'slots': 'Exam Slots',
        'bookings': 'Bookings'
    };
    document.getElementById('page-title').innerText = titles[sectionId];

    if (sectionId === 'users') fetchUsers();
    if (sectionId === 'slots') fetchSlots();
    if (sectionId === 'bookings') {
        fetchBookings();
        populateBookingDropdowns();
    }
}

function showToast(message) {
    const toast = document.getElementById('toast');
    toast.innerText = message;
    toast.classList.add('show');
    setTimeout(() => toast.classList.remove('show'), 3000);
}

// --- API Calls & DOM Manipulation ---

// USERS
async function fetchUsers() {
    const res = await fetch(`${API_BASE}/users`);
    const users = await res.json();
    const tbody = document.querySelector('#users-table tbody');
    tbody.innerHTML = users.map(u => `
        <tr>
            <td>${u.userId}</td>
            <td>${u.name}</td>
            <td>${u.email}</td>
            <td><button class="btn-delete" onclick="deleteUser(${u.userId})">Delete</button></td>
        </tr>
    `).join('');
}

document.getElementById('user-form').addEventListener('submit', async (e) => {
    e.preventDefault();
    const user = {
        userId: parseInt(document.getElementById('userId').value),
        name: document.getElementById('userName').value,
        email: document.getElementById('userEmail').value
    };
    
    const res = await fetch(`${API_BASE}/users`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(user)
    });
    
    if (res.ok) {
        showToast('User Created Successfully');
        document.getElementById('user-form').reset();
        fetchUsers();
    } else {
        showToast('Failed to create user');
    }
});

async function deleteUser(id) {
    if(!confirm('Are you sure you want to delete this user?')) return;
    const res = await fetch(`${API_BASE}/users/${id}`, { method: 'DELETE' });
    if(res.ok) {
        showToast('User Deleted');
        fetchUsers();
    }
}


// SLOTS
async function fetchSlots() {
    const res = await fetch(`${API_BASE}/slots`);
    const slots = await res.json();
    const tbody = document.querySelector('#slots-table tbody');
    tbody.innerHTML = slots.map(s => `
        <tr>
            <td>${s.slotId}</td>
            <td>${s.date}</td>
            <td>${s.time}</td>
            <td>${s.loc}</td>
            <td style="color: ${s.status === 'Available' ? '#1dd1a1' : '#ff4757'}">${s.status}</td>
            <td><button class="btn-delete" onclick="deleteSlot(${s.slotId})">Delete</button></td>
        </tr>
    `).join('');
}

document.getElementById('slot-form').addEventListener('submit', async (e) => {
    e.preventDefault();
    const slot = {
        slotId: parseInt(document.getElementById('slotId').value),
        date: document.getElementById('slotDate').value,
        time: document.getElementById('slotTime').value,
        loc: document.getElementById('slotLoc').value
    };
    
    const res = await fetch(`${API_BASE}/slots`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(slot)
    });
    
    if (res.ok) {
        showToast('Slot Created Successfully');
        document.getElementById('slot-form').reset();
        fetchSlots();
    } else {
        showToast('Failed to create slot');
    }
});

async function deleteSlot(id) {
    if(!confirm('Are you sure?')) return;
    const res = await fetch(`${API_BASE}/slots/${id}`, { method: 'DELETE' });
    if(res.ok) {
        showToast('Slot Deleted');
        fetchSlots();
    }
}


// BOOKINGS
async function fetchBookings() {
    const res = await fetch(`${API_BASE}/bookings`);
    const bookings = await res.json();
    const tbody = document.querySelector('#bookings-table tbody');
    tbody.innerHTML = bookings.map(b => `
        <tr>
            <td>${b.bookingId}</td>
            <td>${b.userName}</td>
            <td>${b.date}</td>
            <td>${b.time}</td>
            <td><button class="btn-delete" onclick="deleteBooking(${b.bookingId})">Delete</button></td>
        </tr>
    `).join('');
}

async function populateBookingDropdowns() {
    const userRes = await fetch(`${API_BASE}/users`);
    const users = await userRes.json();
    
    const slotRes = await fetch(`${API_BASE}/slots/available`);
    const slots = await slotRes.json();

    document.getElementById('bookUserId').innerHTML = 
        `<option value="">Select User</option>` + 
        users.map(u => `<option value="${u.userId}">${u.name} (ID: ${u.userId})</option>`).join('');

    document.getElementById('bookSlotId').innerHTML = 
        `<option value="">Select Available Slot</option>` + 
        slots.map(s => `<option value="${s.slotId}">${s.date} | ${s.time} | ${s.loc}</option>`).join('');
}

document.getElementById('booking-form').addEventListener('submit', async (e) => {
    e.preventDefault();
    const booking = {
        bookingId: parseInt(document.getElementById('bookId').value),
        userId: parseInt(document.getElementById('bookUserId').value),
        slotId: parseInt(document.getElementById('bookSlotId').value)
    };
    
    const res = await fetch(`${API_BASE}/bookings`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(booking)
    });
    
    if (res.ok) {
        showToast('Booking Successful!');
        document.getElementById('booking-form').reset();
        fetchBookings();
        populateBookingDropdowns();
    } else {
        showToast('Slot already booked or invalid request');
    }
});

async function deleteBooking(id) {
    if(!confirm('Delete this booking?')) return;
    const res = await fetch(`${API_BASE}/bookings/${id}`, { method: 'DELETE' });
    if(res.ok) {
        showToast('Booking Deleted');
        fetchBookings();
    }
}

// Init
window.onload = () => {
    fetchUsers();
};
