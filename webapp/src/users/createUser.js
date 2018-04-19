// @flow
import {createSagas} from "redux-box";
import {call, put} from 'redux-saga/effects'
import {httpClient} from "../api";

/** The name of the module */
const CREATE_USER_MODULE = "USERS/CREATE_USER";

/** The action key for checking if a user exists */
const CREATE_USER_ACTION = CREATE_USER_MODULE + "/CREATE_USER";
/** The action key for checking if a user exists */
const CREATE_USER_SUCCESS_ACTION = CREATE_USER_MODULE + "/CREATE_USER/SUCCESS";
/** The action key for checking if a user exists */
const CREATE_USER_FAILURE_ACTION = CREATE_USER_MODULE + "/CREATE_USER/FAILURE";

/** The system is still trying to create the user */
export const CREATE_USER_STATUS_LOADING = 'loading';
/** The user creation was a success */
export const CREATE_USER_STATUS_SUCCESS = 'success';
/** The user creation was a failure */
export const CREATE_USER_STATUS_FAILURE = 'failure';

/** The shape of the state for this module */
type State = {
    status?: string
}

const actions = {
    createUser: (email: string, password: string, displayName: string) => ({ type: CREATE_USER_ACTION, email, password, displayName })
};

const mutations = {
    [CREATE_USER_ACTION]: (state: State) => {
        state.status = CREATE_USER_STATUS_LOADING;
    },
    [CREATE_USER_SUCCESS_ACTION]: (state: State) => {
        state.status = CREATE_USER_STATUS_SUCCESS;
    },
    [CREATE_USER_FAILURE_ACTION]: (state: State) => {
        state.status = CREATE_USER_STATUS_FAILURE;
    }
};

const sagas = createSagas({
    [CREATE_USER_ACTION]: function*(action) {
        try {
            yield call(httpClient.post, '/users', {
                email: action.email,
                password: action.password,
                displayName: action.displayName
            });
            yield put({
                type: CREATE_USER_SUCCESS_ACTION
            });
        } catch (e) {
            yield put({
                type: CREATE_USER_FAILURE_ACTION
            });
        }
    }
});

export const createUserModule = {
    name: CREATE_USER_MODULE,
    state: {},
    actions,
    mutations,
    sagas
};
