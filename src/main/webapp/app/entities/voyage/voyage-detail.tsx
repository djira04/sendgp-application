import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './voyage.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IVoyageDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const VoyageDetail = (props: IVoyageDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { voyageEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="voyageDetailsHeading">
          <Translate contentKey="sengpApp.voyage.detail.title">Voyage</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{voyageEntity.id}</dd>
          <dt>
            <span id="depatureCountry">
              <Translate contentKey="sengpApp.voyage.depatureCountry">Depature Country</Translate>
            </span>
          </dt>
          <dd>{voyageEntity.depatureCountry}</dd>
          <dt>
            <span id="depatureAddress">
              <Translate contentKey="sengpApp.voyage.depatureAddress">Depature Address</Translate>
            </span>
          </dt>
          <dd>{voyageEntity.depatureAddress}</dd>
          <dt>
            <span id="depatureCity">
              <Translate contentKey="sengpApp.voyage.depatureCity">Depature City</Translate>
            </span>
          </dt>
          <dd>{voyageEntity.depatureCity}</dd>
          <dt>
            <span id="depatureDate">
              <Translate contentKey="sengpApp.voyage.depatureDate">Depature Date</Translate>
            </span>
          </dt>
          <dd>
            {voyageEntity.depatureDate ? <TextFormat value={voyageEntity.depatureDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="depatureTime">
              <Translate contentKey="sengpApp.voyage.depatureTime">Depature Time</Translate>
            </span>
          </dt>
          <dd>
            {voyageEntity.depatureTime ? <TextFormat value={voyageEntity.depatureTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="arrivalCountry">
              <Translate contentKey="sengpApp.voyage.arrivalCountry">Arrival Country</Translate>
            </span>
          </dt>
          <dd>{voyageEntity.arrivalCountry}</dd>
          <dt>
            <span id="arrivalAddress">
              <Translate contentKey="sengpApp.voyage.arrivalAddress">Arrival Address</Translate>
            </span>
          </dt>
          <dd>{voyageEntity.arrivalAddress}</dd>
          <dt>
            <span id="cityArrival">
              <Translate contentKey="sengpApp.voyage.cityArrival">City Arrival</Translate>
            </span>
          </dt>
          <dd>{voyageEntity.cityArrival}</dd>
          <dt>
            <span id="dateArrival">
              <Translate contentKey="sengpApp.voyage.dateArrival">Date Arrival</Translate>
            </span>
          </dt>
          <dd>{voyageEntity.dateArrival ? <TextFormat value={voyageEntity.dateArrival} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="arrivalTime">
              <Translate contentKey="sengpApp.voyage.arrivalTime">Arrival Time</Translate>
            </span>
          </dt>
          <dd>{voyageEntity.arrivalTime ? <TextFormat value={voyageEntity.arrivalTime} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="kilos">
              <Translate contentKey="sengpApp.voyage.kilos">Kilos</Translate>
            </span>
          </dt>
          <dd>{voyageEntity.kilos}</dd>
          <dt>
            <span id="unitPrice">
              <Translate contentKey="sengpApp.voyage.unitPrice">Unit Price</Translate>
            </span>
          </dt>
          <dd>{voyageEntity.unitPrice}</dd>
          <dt>
            <span id="valid">
              <Translate contentKey="sengpApp.voyage.valid">Valid</Translate>
            </span>
          </dt>
          <dd>{voyageEntity.valid ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="sengpApp.voyage.billet">Billet</Translate>
          </dt>
          <dd>{voyageEntity.billet ? voyageEntity.billet.id : ''}</dd>
          <dt>
            <Translate contentKey="sengpApp.voyage.voyageur">Voyageur</Translate>
          </dt>
          <dd>{voyageEntity.voyageur ? voyageEntity.voyageur.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/voyage" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/voyage/${voyageEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ voyage }: IRootState) => ({
  voyageEntity: voyage.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(VoyageDetail);
