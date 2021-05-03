import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './colis.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IColisDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ColisDetail = (props: IColisDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { colisEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="colisDetailsHeading">
          <Translate contentKey="sengpApp.colis.detail.title">Colis</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{colisEntity.id}</dd>
          <dt>
            <span id="weight">
              <Translate contentKey="sengpApp.colis.weight">Weight</Translate>
            </span>
          </dt>
          <dd>{colisEntity.weight}</dd>
          <dt>
            <span id="details">
              <Translate contentKey="sengpApp.colis.details">Details</Translate>
            </span>
          </dt>
          <dd>{colisEntity.details}</dd>
          <dt>
            <Translate contentKey="sengpApp.colis.voyage">Voyage</Translate>
          </dt>
          <dd>{colisEntity.voyage ? colisEntity.voyage.id : ''}</dd>
          <dt>
            <Translate contentKey="sengpApp.colis.client">Client</Translate>
          </dt>
          <dd>{colisEntity.client ? colisEntity.client.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/colis" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/colis/${colisEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ colis }: IRootState) => ({
  colisEntity: colis.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ColisDetail);
