import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './billet.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBilletDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const BilletDetail = (props: IBilletDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { billetEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="billetDetailsHeading">
          <Translate contentKey="sengpApp.billet.detail.title">Billet</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{billetEntity.id}</dd>
          <dt>
            <span id="label">
              <Translate contentKey="sengpApp.billet.label">Label</Translate>
            </span>
          </dt>
          <dd>{billetEntity.label}</dd>
          <dt>
            <span id="url">
              <Translate contentKey="sengpApp.billet.url">Url</Translate>
            </span>
          </dt>
          <dd>{billetEntity.url}</dd>
          <dt>
            <span id="website">
              <Translate contentKey="sengpApp.billet.website">Website</Translate>
            </span>
          </dt>
          <dd>{billetEntity.website}</dd>
          <dt>
            <Translate contentKey="sengpApp.billet.voyageur">Voyageur</Translate>
          </dt>
          <dd>{billetEntity.voyageur ? billetEntity.voyageur.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/billet" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/billet/${billetEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ billet }: IRootState) => ({
  billetEntity: billet.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(BilletDetail);
