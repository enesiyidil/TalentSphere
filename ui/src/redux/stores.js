import { configureStore } from '@reduxjs/toolkit'
import { userReducer } from './reducers'

const defaultState = {
    role: "",
    token: "",
    authId: 0,
    userProfile: {},
    data: {}
}

export const store = configureStore({
    reducer: userReducer,
    preloadedState: defaultState
})