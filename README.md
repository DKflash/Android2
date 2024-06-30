# Trading App

This project is an Android application designed for both customers and traders. It allows traders to execute trades and customers to view their portfolio and trade history. The app utilizes Retrofit for API calls to fetch current prices from Binance and manage trade data.

## Table of Contents

- [Features](#features)
- [Architecture](#architecture)
- [Setup](#setup)
- [Usage](#usage)
- [APIs Used](#apis-used)

## Features

### Trader Functionality
- **Execute trades** by buying and selling at current market prices.
- **View a list of executed trades.**

### Customer Functionality
- **View the current portfolio value.**
- **View a detailed list of executed trades.**

### Common Functionality
- **Fetch real-time prices from Binance API.**
- **Display trades in a card view format.**



## Architecture

- **MVVM (Model-View-ViewModel)**: The app follows the MVVM architecture to separate concerns and enhance testability.
- **Retrofit**: Used for making network requests.
- **RecyclerView**: Used for displaying lists of trades.
- **CardView**: Used for displaying trade items in a card format.

## Usage

### Trader App

#### Execute a Trade
1. **Launch the app and switch to the Trader section.**
2. **The app will display the current BTC price.**
3. **Click the Buy button to execute a trade at the current price.**
4. **The Buy button will change to Sell. Click it to execute a sell trade.**

#### View Trades
- **All executed trades will be displayed below the trading section in a card format.**

### Customer App

#### View Portfolio
1. **Launch the app and switch to the Customer section.**
2. **The app will display the total portfolio value based on executed trades.**

#### View Trade History
- **All executed trades will be displayed in a card format.**

## APIs Used

### Binance API
**For fetching real-time prices.**

- **Endpoint:** `https://api.binance.com/api/v3/ticker/price?symbol=BTCUSDT`

### Our Server
**We made a server using Node.js to store and retrieve trade data.**

### Trade API
**For managing trade data (mock API).**

- **Endpoints:**
  - `**GET /trades:**` **Fetch all trades.**
  - `**POST /trade:**` **Execute a trade.**


https://github.com/DKflash/Android2/assets/100863436/150a0094-d021-486a-ab1d-9ad7d6538545


