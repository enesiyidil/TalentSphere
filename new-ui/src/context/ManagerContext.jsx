import {createContext, useContext, useEffect, useState} from "react";
import {ApiContext} from "./ApiContext";
import PropTypes from "prop-types";
import {useDispatch, useSelector} from "react-redux";
import {
    addCompany,
    addHoliday,
    addPersonal,
    deleteCompany,
    deleteHoliday,
    deletePersonal,
    setData,
    setUserProfile
} from "../redux/actions.js";
import {
    API_GATEWAY_URL,
    COMPANY_URL,
    DELETE_URL,
    FIND_ALL_BY_COMPANY_ID_URL,
    FIND_ALL_BY_MANAGER_ID_URL,
    HOLIDAY_URL,
    PERSONAL_URL,
    SAVE_URL
} from "../constant/Endpoints.js";

export const ManagerContext = createContext();

export const ManagerContextProvider = ({children}) => {
    const userProfile = useSelector((state) => state.userProfile);
    const token = useSelector((state) => state.token);
    const dispatch = useDispatch();
    const {apiGet, apiPost, apiPatch, apiDelete} = useContext(ApiContext);
    const [isLoading, setIsLoading] = useState(false);
    const [manager, setManager] = useState({
        companies: [],
        personals: [],
        holidays: [],
        payments: []
    });

    async function handleSetData() {
        setIsLoading(true);
        const responseDataCompanies = await apiGet(`${API_GATEWAY_URL}${COMPANY_URL}${FIND_ALL_BY_MANAGER_ID_URL}?managerId=${userProfile.id}`, token);
        if (responseDataCompanies.status === 200) {
            setManager(prevState => ({
                ...prevState,
                companies: responseDataCompanies.data,
                holidays: prevState.holidays || [],
                personals: prevState.personals || [],
                payments: prevState.payments || [],
            }));
        } else {
            // todo: error message and navigate to home page
        }

        const responseDataPersonals = await apiGet(`${API_GATEWAY_URL}${PERSONAL_URL}${FIND_ALL_BY_MANAGER_ID_URL}?managerId=${userProfile.id}`, token);
        if (responseDataCompanies.status === 200) {
            setManager(prevState => ({
                ...prevState,
                personals: responseDataPersonals.data,
                holidays: prevState.holidays || [],
                companies: prevState.companies || [],
                payments: prevState.payments || [],
            }));
        } else {
            // todo: error message and navigate to home page
        }

        setManager(prevState => ({
            ...prevState,
            companies: prevState.companies || [],
            personals: prevState.personals || [],
            payments: prevState.payments || [],
            holidays: prevState.companies.map( company =>
                apiGet(`${API_GATEWAY_URL}${HOLIDAY_URL}${FIND_ALL_BY_COMPANY_ID_URL}?companyId=${company.id}`, token).then(response => response.data)
            )
        }));

        setIsLoading(false);
    }

    useEffect(() => {
        console.log("Manager context set data =>", manager);
        dispatch(setData(manager))
        dispatch(setUserProfile({...userProfile, companies: manager.companies.map(company => company.id)}))
    }, [manager]);

    async function handleAddPersonal(personal) {
        setIsLoading(true);
        const responseData = await apiPost(`${API_GATEWAY_URL}${PERSONAL_URL}${SAVE_URL}`, personal, token);
        if (responseData.status === 200) {
            dispatch(addPersonal(responseData.data));
        } else {
            // todo: error message and navigate to home page
        }
        setIsLoading(false);
    }

    async function handleDeletePersonal(id) {
        setIsLoading(true);
        const responseData = await apiDelete(`${API_GATEWAY_URL}${PERSONAL_URL}${DELETE_URL}?id=${id}\``, token);
        if (responseData.status === 200) {
            dispatch(deletePersonal(id));
        } else {
            // todo: error message and navigate to home page
        }
        setIsLoading(false);
    }

    async function handleAddCompany(company) {
        setIsLoading(true);
        const responseData = await apiPost(`${API_GATEWAY_URL}${COMPANY_URL}${SAVE_URL}`, company, token);
        if (responseData.status === 200) {
            dispatch(addCompany(responseData.data));
        } else {
            // todo: error message and navigate to home page
        }
        setIsLoading(false);
    }

    async function handleDeleteCompany(id) {
        setIsLoading(true);
        const responseData = await apiDelete(`${API_GATEWAY_URL}${COMPANY_URL}${DELETE_URL}?id=${id}\``, token);
        if (responseData.status === 200) {
            dispatch(deleteCompany(id));
        } else {
            // todo: error message and navigate to home page
        }
        setIsLoading(false);
    }

    async function handleAddHoliday(holiday) {
        setIsLoading(true);
        const responseData = await apiPost(`${API_GATEWAY_URL}${HOLIDAY_URL}${SAVE_URL}`, holiday, token);
        if (responseData.status === 200) {
            dispatch(addHoliday(responseData.data));
        } else {
            // todo: error message and navigate to home page
        }
        setIsLoading(false);
    }

    async function handleDeleteHoliday(id) {
        setIsLoading(true);
        const responseData = await apiDelete(`${API_GATEWAY_URL}${HOLIDAY_URL}${DELETE_URL}?id=${id}\``, token);
        if (responseData.status === 200) {
            dispatch(deleteHoliday(id));
        } else {
            // todo: error message and navigate to home page
        }
        setIsLoading(false);
    }

    return (
        <ManagerContext.Provider
            value={{
                handleSetData,
                handleAddPersonal,
                handleDeletePersonal,
                handleAddCompany,
                handleDeleteCompany,
                handleAddHoliday,
                handleDeleteHoliday,
                isLoading,
            }}
        >
            {children}
        </ManagerContext.Provider>
    );
};

ManagerContextProvider.propTypes = {
    children: PropTypes.node.isRequired,
};