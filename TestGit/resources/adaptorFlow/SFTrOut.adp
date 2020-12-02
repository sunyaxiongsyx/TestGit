<?xml version="1.0" encoding="utf-8"?>
<flows xmlns="http://com/dc/esb/xmlbean/adaptor/flow">
	<flow name="SFTrOut"  location="out" flowType="adaptorFlow" description="存储转发接出流程-穿透">
		<service id="StoreForwardSystemIdentifyService" forcedExec="false" rule="" >
		</service>
		<service id="MonitorStep2BService" forcedExec="true" rule="" >
			<parameters>
			</parameters>
		</service>
		<service id="default" forcedExec="false" rule="" >
			<parameters>
			</parameters>
		</service>
		<service id="MonitorStep3BService" forcedExec="true" rule="" >
			<parameters>
			</parameters>
		</service>
		<attributes>
			<attribute owner="common" key="through" value="true"/>
		</attributes>
		<parameters>
		</parameters>
	</flow>
</flows>