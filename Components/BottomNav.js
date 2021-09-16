import React from 'react';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import { BottomNavigation, BottomNavigationTab, Icon } from '@ui-kitten/components';
import { SettingsScreen } from './pages/Settings';
import { SnapScreen } from './pages/Snaps';
import { HomeScreen } from './pages/Home';
import { ChatScreen } from './pages/Chats';
import { FaqScreen } from './pages/Faq';

const { Navigator, Screen } = createBottomTabNavigator();

const HomeIcon 		= props => <Icon {...props} name="home-outline" />;
const ImageIcon 	= props => <Icon {...props} name="image-outline" />;
const ChatIcon 		= props => <Icon {...props} name="message-circle-outline" />;
const SettingsIcon 	= props => <Icon {...props} name="settings-outline" />;
const FaqIcon		= props => <Icon {...props} name="question-mark-circle-outline" />;

const BottomTabBar = ({ navigation, state }) => (
	<BottomNavigation selectedIndex={state.index} onSelect={index => navigation.navigate(state.routeNames[index])}>
		<BottomNavigationTab icon={HomeIcon} title="HOME" />
		<BottomNavigationTab icon={ImageIcon} title="SNAPS" />
		<BottomNavigationTab icon={ChatIcon} title="CHATS" />
		<BottomNavigationTab icon={SettingsIcon} title="SETTINGS" />
		<BottomNavigationTab icon={FaqIcon} title="FAQ" />
	</BottomNavigation>
);

export const TabNavigator = () => (
	<Navigator tabBar={props => <BottomTabBar {...props} />} screenOptions={{ headerShown: false }}>
		<Screen name='HOME' component={HomeScreen} />
		<Screen name='SNAPS' component={SnapScreen} />
		<Screen name='CHATS' component={ChatScreen} />
		<Screen name='SETTINGS' component={SettingsScreen} />
		<Screen name='FAQ' component={FaqScreen} />
	</Navigator>
);