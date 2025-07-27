# 🛒 Hardware Eshop App

A modern E-Commerce Android app built with **Kotlin**, structured using **MVVM** and **Clean Architecture** principles. It allows users to register, log in, browse hardware products, manage their cart, and securely complete purchases using **Stripe**.

---

## 🔧 Tech Stack

- **Kotlin**
- **MVVM Architecture**
- **Clean Architecture**
- **Room** – Local database for user authentication and product storage
- **Retrofit** – API integration
- **Stripe API** – Payment processing
- **LiveData + ViewModel** – Reactive UI updates
- **Kotlin Coroutines** – Async operations

---

## 🛍 Features

- 🔐 **User Authentication**
  - Register & login (local Room DB)
- 🧾 **Product Catalog**
  - View detailed hardware product listings
- 🛒 **Shopping Cart**
  - Add/remove products with quantity support
  - Real-time total price updates
- 💳 **Stripe Checkout**
  - Secure payments using Stripe integration
- 📦 **Order Confirmation**
  - Confirmation after successful payment

---

## 📸 Screenshots

### 🏠 Home & Product Pages
<p float="left">
   <img src="https://github.com/Ahmed-Ashraf24/Hardware-E-Shop/blob/main/app/src/main/res/screens/home.png?raw=true" width="250" />
   <img src="https://github.com/Ahmed-Ashraf24/Hardware-E-Shop/blob/main/app/src/main/res/screens/home2.png?raw=true" width="250" />

   <img src="https://github.com/Ahmed-Ashraf24/Hardware-E-Shop/blob/main/app/src/main/res/screens/itempage1.png?raw=true" width="250" />
   <img src="https://github.com/Ahmed-Ashraf24/Hardware-E-Shop/blob/main/app/src/main/res/screens/itempage2.png?raw=true" width="250" />

</p>

### 🛒 Cart & Order Pages
<p float="left">
   <img src="https://github.com/Ahmed-Ashraf24/Hardware-E-Shop/blob/main/app/src/main/res/screens/cartpage.png?raw=true" width="250" />
   <img src="https://github.com/Ahmed-Ashraf24/Hardware-E-Shop/blob/main/app/src/main/res/screens/orderpage.png?raw=true" width="250" />
</p>

### 💳 Payment Screens
<p float="left">
   <img src="https://github.com/Ahmed-Ashraf24/Hardware-E-Shop/blob/main/app/src/main/res/screens/payyment1.png?raw=true" width="250" />
   <img src="https://github.com/Ahmed-Ashraf24/Hardware-E-Shop/blob/main/app/src/main/res/screens/payment2.png?raw=true" width="250" />
</p>

### 🔍 Filter & Search
<p float="left">
   <img src="https://github.com/Ahmed-Ashraf24/Hardware-E-Shop/blob/main/app/src/main/res/screens/filterpage.png?raw=true" width="250" />
   <img src="https://github.com/Ahmed-Ashraf24/Hardware-E-Shop/blob/main/app/src/main/res/screens/search.png?raw=true" width="250" />
</p>

### 👤 Profile Pages
<p float="left">
   <img src="https://github.com/Ahmed-Ashraf24/Hardware-E-Shop/blob/main/app/src/main/res/screens/profile%201.png?raw=true" width="250" />
   <img src="https://github.com/Ahmed-Ashraf24/Hardware-E-Shop/blob/main/app/src/main/res/screens/profile2.png?raw=true" width="250" />
</p>

---

## 🧠 Clean Architecture Structure

📦 data

┣ 📂 local # Room DB and DAO
┣ 📂 remote # Retrofit API services
┣ 📂 repository # Implementation of repositories

📦 domain
┣ 📂 model # Business models
┣ 📂 usecase # Business logic (use cases)

📦 presentation
┣ 📂 viewmodels # ViewModel classes for UI logic
┣ 📂 ui # Activities, Fragments, Adapters, etc.

---

## 🚀 Getting Started

1. **Clone the repo**
   ```bash
   git clone https://github.com/Ahmed-Ashraf24/Hardware-E-Shop.git
