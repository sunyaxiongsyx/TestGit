<?xml version="1.0" encoding="utf-8"?>
<flows xmlns="http://com/dc/esb/xmlbean/adaptor/flow">
	<flow name="PROTOCOL_RES"  location="in" flowType="protocolFlow" description="默认响应流程">
		<service id="ExceptionHandlerService" forcedExec="true" rule="" >
			<parameters>
			</parameters>
		</service>
		<service id="ExceptionPackService" forcedExec="true" rule="" >
			<parameters>
			</parameters>
		</service>
		<service id="ProtocolWriteService" forcedExec="true" rule="" >
			<parameters>
			</parameters>
		</service>
		<attributes>
		</attributes>
		<parameters>
		</parameters>
	</flow>
</flows>