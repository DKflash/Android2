const express = require('express');
const mongoose = require('mongoose');
const bodyParser = require('body-parser');
const cors = require('cors');

const app = express();
const port = 3000;

app.use(bodyParser.json());
app.use(cors());

// Update connection string
mongoose.connect('mongodb://127.0.0.1:27017/trades', { useNewUrlParser: true, useUnifiedTopology: true })
    .then(() => console.log('MongoDB connected'))
    .catch(err => console.error('MongoDB connection error:', err));

const tradeSchema = new mongoose.Schema({
    symbol: String,
    entryPrice: Number,
    exitPrice: Number,
    profit: Number
});

const Trade = mongoose.model('Trade', tradeSchema);

app.post('/trade', async (req, res) => {
    const tradeData = req.body;
    tradeData.profit = tradeData.exitPrice - tradeData.entryPrice; // Calculate profit before saving
    const trade = new Trade(tradeData);
    try {
        await trade.save();

        console.log(trade);
        res.status(201).send(trade);
    } catch (error) {
        res.status(400).send(error);
    }
});

app.get('/trades', async (req, res) => {
    try {
        const trades = await Trade.find();
        res.status(200).send(trades);
    } catch (error) {
        res.status(500).send(error);
    }
});

app.listen(port, () => {
    console.log(`Server running at http://localhost:${port}`);
});
