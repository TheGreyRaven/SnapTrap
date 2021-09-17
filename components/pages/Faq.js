import React from 'react';
import { Linking, ScrollView, StyleSheet, View} from 'react-native';
import { Button, Card, Text } from '@ui-kitten/components';

const Header = (props) => (
	<View {...props}>
		<Text category='h5' style={styles.warningHead} status='primary'>FAQ!</Text>
		<Text category='s1'>Please read this through carefully!</Text>
	</View>
);

const Footer = (props) => (
	<View {...props} style={[props.style, styles.footerContainer]}>
		<Button
			style={styles.btn}
			size='small'
			onPress={() => { Linking.openURL('https://google.com') }}
		>
			DISCORD
		</Button>
	</View>
);

export const FaqScreen = () => (
	<ScrollView style={styles.main}>
		<Card header={Header} footer={Footer} status='primary'>
			<Text>This app CAN'T do the following as for now:{'\n'}{'\n'}
			- Bypass screenshot detection.{'\n'}
			- Bypass Snapchats root detection.{'\n'}
			- View Snaps in Snapchat undetected.{'\n'}
			- View stories in Snapchat undetected.{'\n'}
			- Save sent and recived chat messages.{'\n'}
			- Send image from gallery.{'\n'}
			- No ad blocking.{'\n'}
			- All other features Snapchat modules have.{'\n'}{'\n'}

			SnapTrap can save recived Snaps and stories without marking them as viewed, but that is not handled through hooking into Snapchat we do all that from the SnapTrap app itself!{'\n'}{'\n'}

			We also want to mention that we collect one type of data just so that we can track number of app downloads!{'\n'}
			</Text>
		</Card>
	</ScrollView>
);

const styles = StyleSheet.create({
	main: {
		flex: 1,
		backgroundColor: '#FBFBFB',
		paddingHorizontal: 8,
		paddingVertical: 10,
	},
	footerContainer: {
		flexDirection: 'row',
		justifyContent: 'center',
	},
	btn: {
		marginHorizontal: 2,
	},
	warningHead: {
		fontWeight: "bold"
	}
});