<?xml version="1.0" encoding="utf-8"?>
<flows xmlns="http://com/dc/esb/xmlbean/adaptor/flow">
	<flow name="ThroughOut"  location="out" flowType="adaptorFlow" description="穿透场景接出流程">
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