import React from 'react';
import { Container, Segment, Grid } from 'semantic-ui-react';
import { Interpolate } from 'react-i18next';
import image from './galaxy.jpg';

export function LandingPage() {
    return (
        <Container>
            <Segment basic padded>
                <Grid>
                    <Grid.Row>
                        <Grid.Column width={12}>
                            <p>
                                <img src={image} width="100%" />
                            </p>
                            <Interpolate i18nKey="page.landing" parent="p"/>
                        </Grid.Column>
                        <Grid.Column width={4}>
                            Right Side
                        </Grid.Column>
                    </Grid.Row>
                </Grid>
            </Segment>
        </Container>
    )
}
