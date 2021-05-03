import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './voyage.reducer';
import { IVoyage } from 'app/shared/model/voyage.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';

export interface IVoyageProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Voyage = (props: IVoyageProps) => {
  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE, 'id'), props.location.search)
  );

  const getAllEntities = () => {
    props.getEntities(paginationState.activePage - 1, paginationState.itemsPerPage, `${paginationState.sort},${paginationState.order}`);
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (props.location.search !== endURL) {
      props.history.push(`${props.location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(props.location.search);
    const page = params.get('page');
    const sort = params.get('sort');
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [props.location.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === 'asc' ? 'desc' : 'asc',
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  const { voyageList, match, loading, totalItems } = props;
  return (
    <div>
      <h2 id="voyage-heading" data-cy="VoyageHeading">
        <Translate contentKey="sengpApp.voyage.home.title">Voyages</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="sengpApp.voyage.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="sengpApp.voyage.home.createLabel">Create new Voyage</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {voyageList && voyageList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="sengpApp.voyage.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('depatureCountry')}>
                  <Translate contentKey="sengpApp.voyage.depatureCountry">Depature Country</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('depatureAddress')}>
                  <Translate contentKey="sengpApp.voyage.depatureAddress">Depature Address</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('depatureCity')}>
                  <Translate contentKey="sengpApp.voyage.depatureCity">Depature City</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('depatureDate')}>
                  <Translate contentKey="sengpApp.voyage.depatureDate">Depature Date</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('depatureTime')}>
                  <Translate contentKey="sengpApp.voyage.depatureTime">Depature Time</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('arrivalCountry')}>
                  <Translate contentKey="sengpApp.voyage.arrivalCountry">Arrival Country</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('arrivalAddress')}>
                  <Translate contentKey="sengpApp.voyage.arrivalAddress">Arrival Address</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('cityArrival')}>
                  <Translate contentKey="sengpApp.voyage.cityArrival">City Arrival</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('dateArrival')}>
                  <Translate contentKey="sengpApp.voyage.dateArrival">Date Arrival</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('arrivalTime')}>
                  <Translate contentKey="sengpApp.voyage.arrivalTime">Arrival Time</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('kilos')}>
                  <Translate contentKey="sengpApp.voyage.kilos">Kilos</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('unitPrice')}>
                  <Translate contentKey="sengpApp.voyage.unitPrice">Unit Price</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('valid')}>
                  <Translate contentKey="sengpApp.voyage.valid">Valid</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="sengpApp.voyage.billet">Billet</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="sengpApp.voyage.voyageur">Voyageur</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {voyageList.map((voyage, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${voyage.id}`} color="link" size="sm">
                      {voyage.id}
                    </Button>
                  </td>
                  <td>{voyage.depatureCountry}</td>
                  <td>{voyage.depatureAddress}</td>
                  <td>{voyage.depatureCity}</td>
                  <td>{voyage.depatureDate ? <TextFormat type="date" value={voyage.depatureDate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{voyage.depatureTime ? <TextFormat type="date" value={voyage.depatureTime} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{voyage.arrivalCountry}</td>
                  <td>{voyage.arrivalAddress}</td>
                  <td>{voyage.cityArrival}</td>
                  <td>{voyage.dateArrival ? <TextFormat type="date" value={voyage.dateArrival} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{voyage.arrivalTime ? <TextFormat type="date" value={voyage.arrivalTime} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{voyage.kilos}</td>
                  <td>{voyage.unitPrice}</td>
                  <td>{voyage.valid ? 'true' : 'false'}</td>
                  <td>{voyage.billet ? <Link to={`billet/${voyage.billet.id}`}>{voyage.billet.id}</Link> : ''}</td>
                  <td>{voyage.voyageur ? <Link to={`voyageur/${voyage.voyageur.id}`}>{voyage.voyageur.id}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${voyage.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${voyage.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${voyage.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="sengpApp.voyage.home.notFound">No Voyages found</Translate>
            </div>
          )
        )}
      </div>
      {props.totalItems ? (
        <div className={voyageList && voyageList.length > 0 ? '' : 'd-none'}>
          <Row className="justify-content-center">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </Row>
          <Row className="justify-content-center">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={props.totalItems}
            />
          </Row>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

const mapStateToProps = ({ voyage }: IRootState) => ({
  voyageList: voyage.entities,
  loading: voyage.loading,
  totalItems: voyage.totalItems,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Voyage);
