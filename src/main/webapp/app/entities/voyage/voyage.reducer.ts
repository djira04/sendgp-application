import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IVoyage, defaultValue } from 'app/shared/model/voyage.model';

export const ACTION_TYPES = {
  FETCH_VOYAGE_LIST: 'voyage/FETCH_VOYAGE_LIST',
  FETCH_VOYAGE: 'voyage/FETCH_VOYAGE',
  CREATE_VOYAGE: 'voyage/CREATE_VOYAGE',
  UPDATE_VOYAGE: 'voyage/UPDATE_VOYAGE',
  PARTIAL_UPDATE_VOYAGE: 'voyage/PARTIAL_UPDATE_VOYAGE',
  DELETE_VOYAGE: 'voyage/DELETE_VOYAGE',
  RESET: 'voyage/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IVoyage>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type VoyageState = Readonly<typeof initialState>;

// Reducer

export default (state: VoyageState = initialState, action): VoyageState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_VOYAGE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_VOYAGE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_VOYAGE):
    case REQUEST(ACTION_TYPES.UPDATE_VOYAGE):
    case REQUEST(ACTION_TYPES.DELETE_VOYAGE):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_VOYAGE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_VOYAGE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_VOYAGE):
    case FAILURE(ACTION_TYPES.CREATE_VOYAGE):
    case FAILURE(ACTION_TYPES.UPDATE_VOYAGE):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_VOYAGE):
    case FAILURE(ACTION_TYPES.DELETE_VOYAGE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_VOYAGE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_VOYAGE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_VOYAGE):
    case SUCCESS(ACTION_TYPES.UPDATE_VOYAGE):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_VOYAGE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_VOYAGE):
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

const apiUrl = 'api/voyages';

// Actions

export const getEntities: ICrudGetAllAction<IVoyage> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_VOYAGE_LIST,
    payload: axios.get<IVoyage>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IVoyage> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_VOYAGE,
    payload: axios.get<IVoyage>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IVoyage> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_VOYAGE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IVoyage> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_VOYAGE,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IVoyage> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_VOYAGE,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IVoyage> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_VOYAGE,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
