import React, { useEffect } from 'react';
import { ScrollView, StyleSheet, View } from 'react-native';
import { Card, Text, Toggle } from '@ui-kitten/components';
import { NativeModules } from 'react-native';
const { SnapSettings } = NativeModules;

export const SettingsScreen = () => {
	const [saveSnaps, setSaveSnaps] = React.useState(true);
	const [disableScreenshot, setDisableScreenshot] = React.useState(true);

	useEffect(async() => {
		const SaveSnaps = await SnapSettings.exportGetSaveSnaps();
		const DisableScreenshot = await SnapSettings.exportGetDisableScreenshot();

		setSaveSnaps(SaveSnaps);
		setDisableScreenshot(DisableScreenshot);
	})

	const renderItemHeader = (headerProps, info) => (
		<View {...headerProps}>
			<Text category='h6'>
				{info}
			</Text>
		</View>
	);

	return (
		<ScrollView style={styles.main}>
			<Card
				style={styles.item}
				status='primary'
				header={headerProps => renderItemHeader(headerProps, 'Save incoming Snaps')}
				footer={footerProps => {
					return (<Text {...footerProps}>
						<Toggle
							style={styles.toggle}
							status='primary'
							checked={saveSnaps}
							onChange={async (isChecked) => {
								setSaveSnaps(isChecked);
								await SnapSettings.exportSetSaveSnaps(isChecked);
							}}>
						</Toggle>
					</Text>)
				}}>
				<Text>
					Automatically save all recived Snaps and don't mark them as viewed!{'\n'}
				</Text>
			</Card>

			<Card
				style={styles.item_last}
				status='primary'
				header={headerProps => renderItemHeader(headerProps, 'Bypass Screenshots')}
				footer={footerProps => {
					return(<Text {...footerProps}>
						<Toggle
							style={styles.toggle}
							status='primary'
							checked={disableScreenshot}
							onChange={async (isChecked) => {
								setDisableScreenshot(isChecked)
								await SnapSettings.exportSetDisableScreenshot(isChecked);
							}}>
						</Toggle>
					</Text>)
				}}>
				<Text>
					Completely bypass Snapchat screenshots detection!
				</Text>
			</Card>

			{/* <Card
				style={styles.item_last}
				status='primary'
				header={headerProps => renderItemHeader(headerProps, 'Disable screenshot detection')}
				footer={footerProps => renderItemFooter(footerProps)}>
				<Text>
					Disable screenshot detection completely!
				</Text>
			</Card> */}

			{/* <Card
				style={styles.item_last}
				status='primary'
				header={headerProps => renderItemHeader(headerProps, 'SafetyNet checks')}
				footer={footerProps => renderItemFooter(footerProps)}>
				<Text>
					Automatically check SafetyNet integrity each time you launch Snapchat!
				</Text>
			</Card> */}

		</ScrollView>
	);
};

/**
 * TODO: Use something like: https://github.com/vitalets/react-native-extended-stylesheet
 */

const styles = StyleSheet.create({
	main: {
		flex: 1,
		backgroundColor: '#FBFBFB',
		paddingHorizontal: 8,
		paddingVertical: 10,
	},
	item: {
		marginVertical: 4,
	},
	item_last: {
		marginVertical: 4,
		marginBottom: 20
	}
});