import {createStore} from 'redux-box';
import {userExistsModule} from '../authentication'

export default createStore([
    userExistsModule
]);
