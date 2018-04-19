import {createStore} from 'redux-box';
import {userExistsModule} from '../authentication'
import {createUserModule} from '../users'

export default createStore([
    userExistsModule,
    createUserModule
]);
