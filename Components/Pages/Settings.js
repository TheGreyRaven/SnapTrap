import React from 'react';
import { ScrollView, StyleSheet, View } from 'react-native';
import { Card, Text, Toggle } from '@ui-kitten/components';

const useToggleState = (initialState = false) => {
	const [checked, setChecked] = React.useState(initialState);

	const onCheckedChange = (isChecked) => {
		setChecked(isChecked);
	};

	return { checked, onChange: onCheckedChange };
};

export const SettingsScreen = () => {

	const saveSnapToggle  = useToggleState();
	const saveStoryToggle = useToggleState();

	const renderItemHeader = (headerProps, info) => (
		<View {...headerProps}>
			<Text category='h6'>
				{info}
			</Text>
		</View>
	);

	const renderItemFooter = (footerProps, toggleType) => (
		<Text {...footerProps}>
			<Toggle
				style={styles.toggle}
				status='primary'
				{...toggleType}>
			</Toggle>
		</Text>
	);

	return (
		<ScrollView style={styles.main}>
			<Card
				style={styles.item}
				status='primary'
				header={headerProps => renderItemHeader(headerProps, 'Save incoming Snaps')}
				footer={footerProps => renderItemFooter(footerProps, saveSnapToggle)}>
				<Text>
					Automatically save all recived Snaps and don't mark them as viewed!{'\n'}
				</Text>
			</Card>

			<Card
				style={styles.item}
				status='primary'
				header={headerProps => renderItemHeader(headerProps, 'Save Snapchat stories')}
				footer={footerProps => renderItemFooter(footerProps, saveStoryToggle)}>
				<Text>
					Automatically save all Snapchat stories and don't mark them as viewed!
				</Text>
			</Card>

		</ScrollView>
	);
};

const styles = StyleSheet.create({
	main: {
		flex: 1,
		backgroundColor: '#FBFBFB',
		paddingHorizontal: 8,
		paddingVertical: 10
	},
	item: {
		marginVertical: 4,
	},
});