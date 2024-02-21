export const userReducer = (state, action) => {
    switch (action.type) {
        case 'RESET':
            return {
                userProfile: {},
                role: "",
                data: {}
            };
        case 'SET_ROLE':
            return {
                ...state,
                role: action.payload
            };
        case 'SET_TOKEN':
            return {
                ...state,
                token: action.payload
            };
        case 'SET_AUTH_ID':
            return {
                ...state,
                authId: action.payload
            };
        case 'SET_SWITCH_ACCOUNT':
            return {
                ...state,
                switchAccount: action.payload
            };
        case 'SET_SEARCH':
            return {
                ...state,
                search: action.payload
            };
        case 'SET_USER_PROFILE':
            return {
                ...state,
                userProfile: action.payload
            };
        case 'UPDATE_USER_PROFILE':
            return {
                ...state,
                userProfile: {...state.userProfile, ...action.payload}
            };
        case 'SET_DATA':
            return {
                ...state,
                data: action.payload
            };
        case 'ADD_PERSONAL':
            return {
                ...state,
                data: {...state.data, personals: [...state.data.personals, action.payload]}
            };
        case 'ADD_COMPANY':
            return {
                ...state,
                data: {...state.data, companies: [...state.data.companies, action.payload]}
            };
        case 'ADD_HOLIDAY':
            return {
                ...state,
                data: {...state.data, holidays: [...state.data.holidays, action.payload]}
            };
        case 'DELETE_PERSONAL':
            return {
                ...state,
                data: {...state.data, personals: state.data.personals.filter((personal) => personal.id !== action.payload)}
            };
        case 'DELETE_COMPANY':
            return {
                ...state,
                data: {...state.data, companies: state.data.companies.filter((company) => company.id !== action.payload)}
            };
        case 'DELETE_HOLIDAY':
            return {
                ...state,
                data: {...state.data, holidays: state.data.holidays.filter((holiday) => holiday.id !== action.payload)}
            };
        default:
            return state;
    }
};

