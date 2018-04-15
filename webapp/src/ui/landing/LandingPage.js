import React from 'react';
import { Container, Segment, Grid } from 'semantic-ui-react';

export function LandingPage() {
    return (
        <Container>
            <Segment basic padded>
                <Grid>
                    <Grid.Row>
                        <Grid.Column width={12}>
                            Left Side
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
