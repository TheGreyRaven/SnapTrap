import React from 'react';
import { ApplicationProvider, IconRegistry, Text } from '@ui-kitten/components';
import * as eva from '@eva-design/eva';
import { EvaIconsPack } from '@ui-kitten/eva-icons';
import { Header } from './Components/Header';
import { TabNavigator } from './Components/BottomNav.js';
import { default as theme } from './custom-theme.json';
import { NavigationContainer } from '@react-navigation/native';

export default () => (
	<>
		<IconRegistry icons={EvaIconsPack} />
		<ApplicationProvider {...eva} theme={{ ...eva.light, ...theme }} style={{ backgroundColor: '#FBFBFB' }}>
			<Header />
			<NavigationContainer>
				<TabNavigator />
			</NavigationContainer>
		</ApplicationProvider>
	</>
);