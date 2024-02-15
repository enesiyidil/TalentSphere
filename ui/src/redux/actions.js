export const resetStore = () => ({
    type: 'RESET'
})

export const setRole = (payload) => ({
    type: 'SET_ROLE',
    payload
})

export const setToken = (payload) => ({
    type: 'SET_TOKEN',
    payload
})

export const setAuthId = (payload) => ({
    type: 'SET_AUTH_ID',
    payload
})

export const setUserProfile = (payload) => ({
    type: 'SET_USER_PROFILE',
    payload
})

export const updateUserProfile = (payload) => ({
    type: 'UPDATE_USER_PROFILE',
    payload
})

export const setData = (payload) => ({
    type: 'SET_DATA',
    payload
})

export const addPersonal = (payload) => ({
    type: 'ADD_PERSONAL',
    payload
})

export const addCompany = (payload) => ({
    type: 'ADD_COMPANY',
    payload
})

export const addHoliday = (payload) => ({
    type: 'ADD_HOLIDAY',
    payload
})

export const deletePersonal = (payload) => ({
    type: 'DELETE_PERSONAL',
    payload
})

export const deleteCompany = (payload) => ({
    type: 'DELETE_COMPANY',
    payload
})

export const deleteHoliday = (payload) => ({
    type: 'DELETE_HOLIDAY',
    payload
})