import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IColis, defaultValue } from 'app/shared/model/colis.model';

export const ACTION_TYPES = {
  FETCH_COLIS_LIST: 'colis/FETCH_COLIS_LIST',
  FETCH_COLIS: 'colis/FETCH_COLIS',
  CREATE_COLIS: 'colis/CREATE_COLIS',
  UPDATE_COLIS: 'colis/UPDATE_COLIS',
  PARTIAL_UPDATE_COLIS: 'colis/PARTIAL_UPDATE_COLIS',
  DELETE_COLIS: 'colis/DELETE_COLIS',
  RESET: 'colis/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IColis>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type ColisState = Readonly<typeof initialState>;

// Reducer

export default (state: ColisState = initialState, action): ColisState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_COLIS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_COLIS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_COLIS):
    case REQUEST(ACTION_TYPES.UPDATE_COLIS):
    case REQUEST(ACTION_TYPES.DELETE_COLIS):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_COLIS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_COLIS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_COLIS):
    case FAILURE(ACTION_TYPES.CREATE_COLIS):
    case FAILURE(ACTION_TYPES.UPDATE_COLIS):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_COLIS):
    case FAILURE(ACTION_TYPES.DELETE_COLIS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_COLIS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_COLIS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_COLIS):
    case SUCCESS(ACTION_TYPES.UPDATE_COLIS):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_COLIS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_COLIS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/colis';

// Actions

export const getEntities: ICrudGetAllAction<IColis> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_COLIS_LIST,
    payload: axios.get<IColis>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IColis> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_COLIS,
    payload: axios.get<IColis>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IColis> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_COLIS,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IColis> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_COLIS,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IColis> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_COLIS,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IColis> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_COLIS,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
