import React from 'react'
import ReactDOM from 'react-dom/client'
import {Provider} from "react-redux";
import App from './App.jsx'
import './index.css'
import {ApiContextProvider} from "./context/ApiContext.jsx";
import {store} from "./redux/stores.js";

ReactDOM.createRoot(document.getElementById('root')).render(
    <React.StrictMode>
        <ApiContextProvider>
            <Provider store={store}>
                <App/>
            </Provider>
        </ApiContextProvider>
    </React.StrictMode>,
)
