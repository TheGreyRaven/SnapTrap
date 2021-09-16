import React from 'react';
import { ApplicationProvider, IconRegistry } from '@ui-kitten/components';
import * as eva from '@eva-design/eva';
import { EvaIconsPack } from '@ui-kitten/eva-icons';
import { Header } from './components/Header';
import { TabNavigator } from './components/BottomNav.js';
import { default as theme } from './custom-theme.json';
import { NavigationContainer } from '@react-navigation/native';
import Matomo from 'react-native-matomo';

export default () => {
	Matomo.initTracker('https://analytics.snaptrap.io/piwik.php', 1);
	Matomo.trackAppDownload();
	return (
		<>
			<IconRegistry icons={EvaIconsPack} />
			<ApplicationProvider {...eva} theme={{ ...eva.light, ...theme }} style={{ backgroundColor: '#FBFBFB' }}>
				<Header />
				<NavigationContainer>
					<TabNavigator />
				</NavigationContainer>
			</ApplicationProvider>
		</>
	)
};