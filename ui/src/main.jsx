import React from 'react'
import ReactDOM from 'react-dom/client'
import {Provider} from "react-redux";
import App from './App.jsx'
import './index.css'
import {ApiContextProvider} from "./context/ApiContext.jsx";
import {store} from "./redux/stores.js";
import {DevSupport} from "@react-buddy/ide-toolbox";
import {ComponentPreviews, useInitial} from "./dev/index.js";

ReactDOM.createRoot(document.getElementById('root')).render(
    <React.StrictMode>
        <ApiContextProvider>
            <Provider store={store}>
                <DevSupport ComponentPreviews={ComponentPreviews}
                            useInitialHook={useInitial}
                >
                    <App/>
                </DevSupport>
            </Provider>
        </ApiContextProvider>
    </React.StrictMode>,
)
